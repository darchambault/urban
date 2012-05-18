package com.urban.simengine.managers.population.firstnamegenerators;

import com.urban.simengine.Gender;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileBasedFirstNameGeneratorTest {
    @Test public void testGetName() throws IOException {
        String expectedFirstName = "John";

        String[] maleLines = {expectedFirstName+",100.0", "fakename1,0.0", "fakename2,0.0"};
        File maleNamesTempDataFile = this.createTempNameFile(maleLines);
        String[] femaleLines = {};
        File femaleNamesTempDataFile = this.createTempNameFile(femaleLines);

        FirstNameGenerator generator = new FileBasedFirstNameGenerator(maleNamesTempDataFile, femaleNamesTempDataFile);
        String firstName = generator.getName(Gender.MALE);

        assertThat(firstName, equalTo(expectedFirstName));
    }

    @Test public void testGenderSeparation() throws IOException {
        String expectedMaleFirstName = "John";
        String expectedFemaleFirstName = "Jane";

        String[] maleLines = {expectedMaleFirstName+",100.0"};
        File maleNamesTempDataFile = this.createTempNameFile(maleLines);
        String[] femaleLines = {expectedFemaleFirstName+",100.0"};
        File femaleNamesTempDataFile = this.createTempNameFile(femaleLines);

        FirstNameGenerator generator = new FileBasedFirstNameGenerator(maleNamesTempDataFile, femaleNamesTempDataFile);

        String maleFirstName = generator.getName(Gender.MALE);
        String femaleFirstName = generator.getName(Gender.FEMALE);

        assertThat(maleFirstName, equalTo(expectedMaleFirstName));
        assertThat(femaleFirstName, equalTo(expectedFemaleFirstName));

    }

    @Test public void testNameDistribution() throws IOException {
        String[] maleLines = {"John,50.0", "James,50.0"};
        File maleNamesTempDataFile = this.createTempNameFile(maleLines);
        String[] femaleLines = {};
        File femaleNamesTempDataFile = this.createTempNameFile(femaleLines);

        FirstNameGenerator generator = new FileBasedFirstNameGenerator(maleNamesTempDataFile, femaleNamesTempDataFile);

        int johnRatio = 0;
        int jamesRatio = 0;
        for (int i = 0; i < 100; i++) {
            String firstName = generator.getName(Gender.MALE);
            if (firstName.equals("John")) {
                johnRatio++;
            } else if (firstName.equals("James")) {
                jamesRatio++;
            }
        }

        assertThat(johnRatio, not(0));
        assertThat(jamesRatio, not(0));
    }

    private File createTempNameFile(String[] lines) throws IOException {
        File dataFile = File.createTempFile("fakenames_", ".csv");
        dataFile.deleteOnExit();

        OutputStreamWriter fileWriter = new FileWriter(dataFile);

        for (String line : lines) {
            fileWriter.write(line + "\n");
        }
        fileWriter.flush();

        return dataFile;
    }
}
