package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import org.junit.jupiter.api.AfterEach;

@SpringBootTest
public class JacksonServiceTest {

    @Autowired
    private JacksonServiceInterface JacksonService;

    private static final String FILE_PATH = "src/test/resource/test.json";

    @AfterEach
    public void tearDown() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoad() throws IOException {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        JsonWrapper jsonWrapper = new JsonWrapper();
        jsonWrapper.setPersons(Collections.singletonList(person));

        JacksonService.saveToFile(FILE_PATH, jsonWrapper);

        JsonWrapper loadedJsonWrapper = JacksonService.loadFromFile(FILE_PATH, JsonWrapper.class);

        assertEquals(1, loadedJsonWrapper.getPersons().size());
        assertEquals("John", loadedJsonWrapper.getPersons().get(0).getFirstName());
        assertEquals("Doe", loadedJsonWrapper.getPersons().get(0).getLastName());
    }

    @Test
    public void testLoadFromFile() throws IOException {
        File file = new File(FILE_PATH);
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        writer.write("{\"persons\":[{\"firstName\":\"Jane\",\"lastName\":\"Doe\"}]}");
        writer.close();

        JsonWrapper loadedJsonWrapper = JacksonService.loadFromFile(FILE_PATH, JsonWrapper.class);

        assertEquals(1, loadedJsonWrapper.getPersons().size());
        assertEquals("Jane", loadedJsonWrapper.getPersons().get(0).getFirstName());
        assertEquals("Doe", loadedJsonWrapper.getPersons().get(0).getLastName());
    }

    @Test
    public void testLoadFromFile_FileNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            JacksonService.loadFromFile("nonExistentFile.json", JsonWrapper.class);
        });

        String expectedMessage = "Failed to load data from file";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
