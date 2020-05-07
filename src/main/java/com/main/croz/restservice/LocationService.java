package com.main.croz.restservice;

import com.main.croz.model.Location;
import com.main.croz.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class LocationService {

    //Allows us the access all the methods inside the validation interface
    @Autowired
    private Validation validation;

    //Both reads and validates the input data
    public File readAndValidateFile(String pathToTheFile) throws IOException {

        //Output file
        File validationResults = new File("src/main/java/ValidationResults.txt");
        //File to read
        File fileToRead = new File(pathToTheFile);
        Scanner myReader = new Scanner(fileToRead);
        //Result StringBuffer
        StringBuilder result = new StringBuilder();


        while (myReader.hasNextLine()) {
            //Read txt file line by line
            String data = myReader.nextLine();
            //Validation check based on the required logic
            if (validation.isAlpha(data) && validation.isDigit(data) && validation.isASCII(data)) {
                //If validation is satisfied then append to result with appropriate VALID string
                result.append("{").append(data).append("VALID").append("}").append("\n");
            } else {
                //Same but with the INVALID string
                result.append("{").append(data).append("INVALID").append("}").append("\n");
            }
        }

        //Close the reader
        myReader.close();

        //Write the result of the validation into the ValidationResult.txt
        Files.write(Paths.get("src/main/java/ValidationResults.txt"), String.valueOf(result).getBytes(), StandardOpenOption.WRITE);

        //Return the validation string of all locations
        return validationResults;
    }

    //Sorts just the locations which are VALID based on the criteria
    public List<Location> listOfValidLocations(File validationResults) throws FileNotFoundException {
        Scanner myReader = new Scanner(validationResults);
        //Resulting list of all locations
        List<Location> listOfLocations = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            //All words inside of one line
            List<String> words = Arrays.asList(data.split(";"));

            //Creating references just for clarification
            String state = words.get(0);
            String city = words.get(1).substring(0, words.get(1).indexOf("="));
            String population = words.get(1).substring(words.get(1).indexOf("=") + 1);
            String isValid = words.get(2).substring(0, words.get(2).length() - 1);


            System.out.println("State: " + state + ", City:" + city + ", population: " + population);

            //If a line has VALID string at the end then add it to the
            //resulting list of Locations which we will present in our JSON
            if(isValid.equalsIgnoreCase("VALID")){
                listOfLocations.add(new Location(state, city, population));
            }
        }
        //Close the reader
        myReader.close();

        //Returning the list of just VALID locations
        return listOfLocations;
    }


}

