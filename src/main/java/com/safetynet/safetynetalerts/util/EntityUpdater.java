package com.safetynet.safetynetalerts.util;

import java.lang.reflect.Field;

public class EntityUpdater {

  /**
   * Update fields of an existing entity with the values of an updated entity.
   * @param <T> the type of the entities
   * @param existing the existing entity
   * @param updated the updated entity
   */
  public static <T> void updateFields(T existing, T updated) {
    try {
      for (Field field : updated.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object newValue = field.get(updated);

        if (newValue != null) {
          field.set(existing, newValue);
        }
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(
        "Error while updating fields of entities: " + e.getMessage(),
        e
      );
    }
  }
}
