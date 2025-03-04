package pers.acp.springboot.core.file.ftp;

import org.apache.ftpserver.ConnectionConfig;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.impl.DefaultConnectionConfig;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import pers.acp.springboot.core.interfaces.IDaemonService;
import pers.acp.springboot.core.daemon.DaemonServiceManager;
import pers.acp.springboot.core.exceptions.FTPServerException;
import pers.acp.springboot.core.file.config.FTPConfig;
import pers.acp.core.CommonTools;
import pers.acp.core.log.LogFactory;
import pers.acp.core.security.MD5Utils;
import pers.acp.core.security.SHA1Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbin on 2016/12/20.
 * FTP服务
 */
public class FTPServer implements Runnable, IDaemonService {

    private final LogFactory log = LogFactory.getInstance(this.getClass());

    private List<FTPServerUser> userList = null;

    private FTPConfig.Listen listen;

    private FtpServer ftpServerInstence = null;

    public FTPServer(List<FTPServerUser> userList, FTPConfig.Listen listen) {
        this.userList = userList;
        this.listen = listen;
    }

    @Override
    public String getServiceName() {
        return "ftp service " + listen.getName();
    }

    @Override
    public void stopService() {
        if (ftpServerInstence != null) {
            ftpServerInstence.stop();
        }
    }

    @Override
    public void run() {
        try {
            if (CommonTools.isNullStr(listen.getDefaultHomeDirectory())) {
                throw new Exception("defaultHomeDirectory is null");
            }
            String defaultHomeDirectory = CommonTools.getAbsPath(listen.getDefaultHomeDirectory());
            FtpServerFactory serverFactory = new FtpServerFactory();
            ListenerFactory factory = new ListenerFactory();
            factory.setPort(listen.getPort());
            serverFactory.addListener("default", factory.createListener());
            ConnectionConfig connectionConfig = new DefaultConnectionConfig(listen.isAnonymousLoginEnabled(), listen.getLoginFailureDelay(), listen.getMaxLogins(), listen.getMaxAnonymousLogins(), listen.getMaxLoginFailures(), listen.getMaxThreads());
            serverFactory.setConnectionConfig(connectionConfig);
            final String pwdMode = listen.getPwdEncryptMode();

            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            userManagerFactory.setPasswordEncryptor(new PasswordEncryptor() {

                @Override
                public String encrypt(String pwd) {
                    String result;
                    switch (pwdMode) {
                        case "MD5":
                            result = MD5Utils.encrypt(pwd);
                            break;
                        case "SHA1":
                            result = SHA1Utils.encrypt(pwd);
                            break;
                        default:
                            result = null;
                    }
                    return result;
                }

                @Override
                public boolean matches(String passwordToCheck, String storedPassword) {
                    return passwordToCheck.equals(storedPassword);
                }

            });
            BaseUser anonymous = new BaseUser();
            anonymous.setName("anonymous");
            anonymous.setEnabled(listen.isAnonymousLoginEnabled());
            anonymous.setHomeDirectory(defaultHomeDirectory);
            if (listen.isAnonymousWritePermission()) {
                List<Authority> authorities = new ArrayList<>();
                authorities.add(new WritePermission());
                anonymous.setAuthorities(authorities);
            }
            serverFactory.getUserManager().save(anonymous);
            if (userList != null && !userList.isEmpty()) {
                for (FTPServerUser ftpServerUser : userList) {
                    BaseUser user = new BaseUser();
                    user.setName(ftpServerUser.getUsername());
                    user.setPassword(ftpServerUser.getPassword());
                    user.setEnabled(ftpServerUser.isEnableflag());
                    String homeDirectory = ftpServerUser.getHomedirectory();
                    if (CommonTools.isNullStr(homeDirectory)) {
                        user.setHomeDirectory(defaultHomeDirectory);
                    } else {
                        homeDirectory = homeDirectory.replace("\\", "/");
                        if (!homeDirectory.startsWith("/")) {
                            homeDirectory = "/" + homeDirectory;
                        }
                        if (defaultHomeDirectory.equals("/")) {
                            user.setHomeDirectory(homeDirectory);
                        } else {
                            user.setHomeDirectory(defaultHomeDirectory + homeDirectory);
                        }
                    }
                    user.setMaxIdleTime(ftpServerUser.getIdletime());
                    List<Authority> authorities = new ArrayList<>();
                    if (ftpServerUser.isWritepermission()) {
                        authorities.add(new WritePermission());
                    }
                    authorities.add(new TransferRatePermission(ftpServerUser.getDownloadrate(), ftpServerUser.getUploadrate()));
                    authorities.add(new ConcurrentLoginPermission(ftpServerUser.getMaxloginnumber(), ftpServerUser.getMaxloginperip()));
                    user.setAuthorities(authorities);
                    serverFactory.getUserManager().save(user);
                }
            } else {
                if (!listen.isAnonymousLoginEnabled()) {
                    log.error("start ftp server failed [" + listen.getName() + "] : no user set!");
                    throw new FTPServerException("no user set");
                }
            }
            ftpServerInstence = serverFactory.createServer();
            ftpServerInstence.start();
            log.info("ftp server [" + listen.getName() + "] is started , path : " + defaultHomeDirectory);
            DaemonServiceManager.addService(this);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("start ftp server failed [" + listen.getName() + "] port:" + listen.getPort());
        }
    }

}
