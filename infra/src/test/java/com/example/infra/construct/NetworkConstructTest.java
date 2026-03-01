package com.example.infra.construct;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;

class NetworkConstructTest {

  @Test
  void testNetworkConstruct() {
    App app = new App();
    Stack stack = new Stack(app, "TestStack");

    NetworkConstruct network = new NetworkConstruct(stack, "TestNetwork");

    assertNotNull(network);
    assertNotNull(network.getVpc());
  }
}
