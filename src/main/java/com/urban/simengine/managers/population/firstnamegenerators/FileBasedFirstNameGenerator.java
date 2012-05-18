package com.urban.simengine.managers.population.firstnamegenerators;

import com.urban.simengine.Gender;

import java.io.*;
import java.util.*;

public class FileBasedFirstNameGenerator implements FirstNameGenerator {
    private Map<Gender, List<String>> names = new HashMap<Gender, List<String>>();
    private Map<Gender, List<Float>> cumulativeWeights = new HashMap<Gender, List<Float>>();

    public FileBasedFirstNameGenerator(File maleNamesDataFile, File femaleNamesDataFile) throws IOException {
        this.initalize(maleNamesDataFile, femaleNamesDataFile);
    }

    private void initalize(File maleNamesDataFile, File femaleNamesDataFile) throws IOException {
        names.put(Gender.MALE, new ArrayList<String>());
        cumulativeWeights.put(Gender.MALE, new ArrayList<Float>());
        names.put(Gender.FEMALE, new ArrayList<String>());
        cumulativeWeights.put(Gender.FEMALE, new ArrayList<Float>());
        this.readFile(Gender.MALE, maleNamesDataFile);
        this.readFile(Gender.FEMALE, femaleNamesDataFile);
    }

    private void readFile(Gender gender, File dataFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
        String strLine;
        float cumulativeWeight = (float) 0.0;
        while ((strLine = bufferedReader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(strLine, ",");
            String name = tokenizer.nextToken();
            float weight = new Float(tokenizer.nextToken());
            cumulativeWeight += weight;
            names.get(gender).add(name.substring(0, 1).toUpperCase()+name.substring(1).toLowerCase());
            cumulativeWeights.get(gender).add(cumulativeWeight);
        }
    }

    public String getName(Gender gender) {
        List<String> names = this.names.get(gender);
        List<Float> cumulativeWeights = this.cumulativeWeights.get(gender);

        float rand = (float) Math.random() * cumulativeWeights.get(cumulativeWeights.size()-1);

        int foundIndex;
        for (foundIndex = 0; foundIndex < cumulativeWeights.size() && rand > cumulativeWeights.get(foundIndex); foundIndex++) {
        }

        return names.get(foundIndex);
    }
}
