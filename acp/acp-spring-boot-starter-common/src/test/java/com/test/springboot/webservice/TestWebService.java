package com.test.springboot.webservice;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 * @author zhangbin
 * @date 2018-1-15 10:41
 * @since JDK1.8
 */
@WebService(endpointInterface = "com.test.springboot.webservice.ITestWebService", serviceName = "test", portName = "testport", targetNamespace = "http://acp.test/")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class TestWebService implements ITestWebService {

    String serviceName = "test";

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int test1(int value1, int value2) {
        return value1 + value2;
    }

    @Override
    public int test2(int value1, int value2) {
        return value1 * value2;
    }
}
