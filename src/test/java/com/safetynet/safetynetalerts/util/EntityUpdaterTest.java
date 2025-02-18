package com.safetynet.safetynetalerts.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EntityUpdaterTest {

  private static class TestEntity {

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

  private static class ChildEntity extends TestEntity {

    private String childField;

    public ChildEntity(
      String stringField,
      int intField,
      boolean booleanField,
      List<String> listField,
      String childField
    ) {
      super(stringField, intField, booleanField, listField);
      this.childField = childField;
    }

    public String getChildField() {
      return childField;
    }
  }

  @Test
  void updateFields_shouldUpdateNonNullFields() {
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
  void updateFields_shouldNotUpdateNullFields() {
    TestEntity existing = new TestEntity(
      "oldString",
      10,
      false,
      Arrays.asList("item1")
    );
    TestEntity updated = new TestEntity(null, 20, true, null);

    EntityUpdater.updateFields(existing, updated);

    assertEquals("oldString", existing.getStringField());
    assertEquals(20, existing.getIntField());
    assertTrue(existing.isBooleanField());
    assertEquals(Arrays.asList("item1"), existing.getListField());
  }

  @Test
  void updateFields_shouldHandleInheritedFields() {
    ChildEntity existing = new ChildEntity(
      "oldString",
      10,
      false,
      Arrays.asList("item1"),
      "oldChild"
    );

    ChildEntity updated = new ChildEntity(
      "newString",
      20,
      true,
      Arrays.asList("item2"),
      "newChild"
    );

    EntityUpdater.updateFields(existing, updated);

    assertEquals("newString", updated.getStringField());
    assertEquals(20, updated.getIntField());
    assertTrue(updated.isBooleanField());
    assertEquals(Arrays.asList("item2"), updated.getListField());
    assertEquals("newChild", updated.getChildField());
  }

  @Test
  void updateFields_shouldHandleDifferentTypes() {
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
  void updateFields_shouldThrowRuntimeExceptionWhenAccessFails() {
    final class InaccessibleField1 {

      @SuppressWarnings("unused")
      private final String finalField1 = "can't change me";
    }

    final class InaccessibleField2 {

      @SuppressWarnings("unused")
      private final String finalField2 = "can't change me";
    }

    InaccessibleField1 existing = new InaccessibleField1();
    InaccessibleField2 updated = new InaccessibleField2();

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      EntityUpdater.updateFields(existing, updated);
    });

    assertEquals(
      "Can not set final java.lang.String field com.safetynet.safetynetalerts.util.EntityUpdaterTest$1InaccessibleField2.finalField2 to com.safetynet.safetynetalerts.util.EntityUpdaterTest$1InaccessibleField1",
      exception.getMessage()
    );
  }
}
