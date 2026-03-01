package com.example.infra.construct;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;

class SoapApiConstructTest {

  @Test
  void testSoapApiConstruct() {
    App app = new App();
    Stack stack = new Stack(app, "TestStack");

    SoapApiConstruct soapApi = new SoapApiConstruct(stack, "TestSoapApi");

    assertNotNull(soapApi);
    assertNotNull(soapApi.getSoapApi());
  }
}
