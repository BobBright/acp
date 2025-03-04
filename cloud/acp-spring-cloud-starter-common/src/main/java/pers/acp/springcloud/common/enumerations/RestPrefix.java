package pers.acp.springcloud.common.enumerations;

/**
 * @author zhangbin by 19/06/2018 18:48
 * @since JDK1.8
 */
public enum RestPrefix {

    API("/api"),

    OPEN("/open");

    RestPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    private String urlPrefix;

}
