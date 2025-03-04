package com.test.springboot.ftp;

import pers.acp.springboot.core.file.ftp.FTPServerUser;
import pers.acp.springboot.core.file.sftp.SFTPServerUser;
import pers.acp.springboot.core.file.user.UserFactory;
import pers.acp.core.CommonTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbin
 * @date 2018-1-15 10:40
 * @since JDK1.8
 */
public class TestUserFactory implements UserFactory {

    @Override
    public List<FTPServerUser> generateFtpUserList() {
        List<FTPServerUser> userList = new ArrayList<>();
        FTPServerUser ftpServerUser = new FTPServerUser();
        ftpServerUser.setUsername("zb");
        ftpServerUser.setPassword("1");
        ftpServerUser.setEnableflag(true);
        ftpServerUser.setWritepermission(true);
        userList.add(ftpServerUser);
        return userList;
    }

    @Override
    public List<SFTPServerUser> generateSFtpUserList() {
        List<SFTPServerUser> userList = new ArrayList<>();
        SFTPServerUser sftpServerUser = new SFTPServerUser();
        sftpServerUser.setUsername("zhang");
        sftpServerUser.setPassword("1");
        //zb
//        sftpServerUser.setPublicKey("AAAAB3NzaC1yc2EAAAADAQABAAABAQDOkYRFzDTr0fk/ZlgOCIDp0p7a/MiK8s7utvw+qJ5n/gFys9QkNjMT1cQaZu5bbVlHbrGqXMAo4Hu53C1nQRPG1Db8WISYbsU5SbMF2hej1GZkGthUZOk8iAv+JDL72unlZGft75U6oJhttLVOn36I3rDcDSzUYc6nXk+Jihn5K2pTiwSKLeAdL4cYXBJwQIplKfmMVUpY+luCgSQhCUp1SGkSr8sK5M54RWLSwrAxli1Em/BjqM1c9cWnLXKxlijMUbUaCQedmrrzVdrv2MU5hIgiG2N4dAm44H6IF86h8scIHiXRgmH0VS9MLHPH9d/ggoja8BTUPeGWEGoUH0E5");
//        sftpServerUser.setPublicKey("AAAAB3NzaC1kc3MAAACBAO+hNa2prmRSPQ9CVuQM2J+enmOiRDcbHIB+exrFXVQBblzAkOmgHnGDfxtRqFjCwNABdtUShWTlTaSLqkw5AWGoWP78bJn9U5hVTb1TEyHloWO5nLZQ1rzenJUvOxjV5e2r2PFpHlwIllZV33wA2HzWq0cFD1Bi17+xfW5YTXfZAAAAFQDAQP9EdMv6GOdhEf04A4j3Hsh4PQAAAIEA6vQLvS55l2XehhDUm7krZ31zI9oPZUml1miuPwtIsuxCObuiER3EvhdSQ59ff4gajfkw+fKqPm+mQ+Ma/i0glatFctwJSGWZENF+O1KjSAcLmdNu6IS5mhNS4zmpj4GS6fOs6VRX1rdH3RgDbB/M1HSJjya1md0bFTZVS5kUwA8AAACABO/C9Phm7ONbDWd+evbeTjHQ9Zal3xq+Qe4Os+EiGW3bfoPCdtei5P6jJbCIdYHfu/mR+GYLW+L/HsXiqrnuW4RnCY7OoXyCX+SeK2jtdPkrfcHIv0tiC65FWZjx274RlYUiJgSfE+tcszUsR6H6pYOwxmN2yRmKla2fSIsCsvc=");
        //yjy
//        sftpServerUser.setPublicKey("AAAAB3NzaC1yc2EAAAADAQABAAABAQCrEkxHw/rCPEB/Xh8vfchtxXIyLnGDnAOXFnIwRLXt+WS9yyir8zi/XJJhXC7zFa5eUHQM+Ji+PFYDEL4tIlhnAWPzt+W6q5Ov69TeBg6pNkSWq//Shvc7NqYn7yjp3aJQHbtI+Zp7fu+kSvcDR+lszI0M/eHLZ0P6J9DHLk54oBGfWWY5KHMr/PHnG3hiP6NbObyBRXB28XEFsCY3bhiLTjHnnGfw4B4Z3DBALX6SDRxyJUFBRaZ9YXr0hlbPOPhkUqZ+IYmu7eHdgObMSIKgOXj0YRHrKtcZ+NXdqDaaBPKnydpJzycniYvmEcvx62mdsgKO9eGyesaG8Q+kxIcp");
        sftpServerUser.setPublicKey(CommonTools.getAbsPath("/files/resource/userpkey/rsa_public_key.pem"));
        sftpServerUser.setEnableflag(true);
        userList.add(sftpServerUser);
        return userList;
    }

}
