package pers.acp.springcloud.server.hello;

import org.springframework.boot.SpringApplication;
import pers.acp.springcloud.common.annotation.AcpCloudAtomApplication;

/**
 * @author zhangbin by 2018-3-5 13:56
 * @since JDK1.8
 */
@AcpCloudAtomApplication
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

}
