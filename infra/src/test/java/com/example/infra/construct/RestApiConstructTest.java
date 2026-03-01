package com.example.infra.construct;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;

class RestApiConstructTest {

  @Test
  void testRestApiConstruct() {
    App app = new App();
    Stack stack = new Stack(app, "TestStack");

    RestApiConstruct restApi = new RestApiConstruct(stack, "TestRestApi");

    assertNotNull(restApi);
    assertNotNull(restApi.getRestApi());
  }
}
