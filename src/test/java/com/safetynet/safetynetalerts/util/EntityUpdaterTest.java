package com.safetynet.safetynetalerts.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EntityUpdaterTest {

  static class TestEntity {

    private String stringField;
    private int intField;
    private boolean booleanField;
    private List<String> listField;

    public TestEntity() {}

    public TestEntity(
      String stringField,
      int intField,
      boolean booleanField,
      List<String> listField
    ) {
      this.stringField = stringField;
      this.intField = intField;
      this.booleanField = booleanField;
      this.listField = listField;
    }

    public String getStringField() {
      return stringField;
    }

    public int getIntField() {
      return intField;
    }

    public boolean isBooleanField() {
      return booleanField;
    }

    public List<String> getListField() {
      return listField;
    }
  }

  @Test
  void testEntityUpdater_Instantiation() {
    EntityUpdater updater = new EntityUpdater();
    assertNotNull(updater);
  }

  @Nested
  class UpdateFields {

    @Test
    void testUpdateFields_shouldUpdateFields() {
      TestEntity existing = new TestEntity(
        "oldString",
        10,
        false,
        Arrays.asList("item1")
      );
      TestEntity updated = new TestEntity(
        "newString",
        20,
        true,
        Arrays.asList("item2")
      );

      EntityUpdater.updateFields(existing, updated);

      assertEquals("newString", existing.getStringField());
      assertEquals(20, existing.getIntField());
      assertTrue(existing.isBooleanField());
      assertEquals(Arrays.asList("item2"), existing.getListField());
    }

    @Test
    void testUpdateFields_shouldHandleDifferentTypes() {
      TestEntity existing = new TestEntity();
      TestEntity updated = new TestEntity(
        "string",
        100,
        true,
        Arrays.asList("item")
      );

      EntityUpdater.updateFields(existing, updated);

      assertEquals("string", existing.getStringField());
      assertEquals(100, existing.getIntField());
      assertTrue(existing.isBooleanField());
      assertEquals(Arrays.asList("item"), existing.getListField());
    }

    @Test
    void testUpdateFields_shouldThrowRuntimeExceptionWhenAccessFails() {
      final class InaccessibleField1 {

        @SuppressWarnings("unused")
        private final String finalField1 = "can't change me";
      }

      final class InaccessibleField2 {

        @SuppressWarnings("unused")
        private final String finalField2 = "can't change me";
      }

      InaccessibleField1 updated = new InaccessibleField1();
      InaccessibleField2 existing = new InaccessibleField2();

      assertThrows(Exception.class, () -> {
        EntityUpdater.updateFields(existing, updated);
      });
    }
  }
}
