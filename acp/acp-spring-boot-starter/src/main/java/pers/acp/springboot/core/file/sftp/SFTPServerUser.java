package pers.acp.springboot.core.file.sftp;

import pers.acp.springboot.core.file.user.ServerUser;

/**
 * Created by zhangbin on 2016/12/20.
 * SFTP用户
 */
public class SFTPServerUser extends ServerUser {

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * 用户公钥
     */
    private String publicKey;

}
