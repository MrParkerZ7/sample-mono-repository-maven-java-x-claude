package com.example.infra.construct;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;

class StorageConstructTest {

  @Test
  void testStorageConstruct() {
    App app = new App();
    Stack stack = new Stack(app, "TestStack");

    StorageConstruct storage = new StorageConstruct(stack, "TestStorage");

    assertNotNull(storage);
    assertNotNull(storage.getBucket());
    assertNotNull(storage.getQueue());
    assertNotNull(storage.getTable());
  }
}
