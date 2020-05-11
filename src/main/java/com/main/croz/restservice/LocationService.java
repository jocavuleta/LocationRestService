package com.main.croz.restservice;

import com.main.croz.model.Location;
import com.main.croz.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class LocationService {

    //Allows us the access all the methods inside the validation interface
    @Qualifier("validationImpl")
    @Autowired
    private Validation validation;


    @Qualifier("validationRegexImpl")
    @Autowired
    private Validation validationRegex;


    //Both reads and validates the input data
    public File readAndValidateFile(String pathToTheFile, String isRegex) throws IOException {

        //Output file
        File validationResults;
        //File to read
        File fileToRead = new File(pathToTheFile);
        Scanner myReader = new Scanner(fileToRead);
        //Result StringBuffer
        StringBuilder result = new StringBuilder();
        Validation oneOfValidations;
        Path fileToWrite;

        if(isRegex.equalsIgnoreCase("regex")){
            oneOfValidations = validationRegex;
            fileToWrite = Paths.get("src/main/java/ValidationResults-regex.txt");
            validationResults = new File("src/main/java/ValidationResults-regex.txt");
        }else{
            oneOfValidations = validation;
            fileToWrite = Paths.get("src/main/java/ValidationResults.txt");
            validationResults = new File("src/main/java/ValidationResults.txt");
        }

        while (myReader.hasNextLine()) {
            //Read txt file line by line
            String data = myReader.nextLine();
            //Validation check based on the required logic
            if (oneOfValidations.isAlpha(data) && oneOfValidations.isDigit(data) && oneOfValidations.isASCII(data)) {
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
        Files.write(fileToWrite, String.valueOf(result).getBytes(), StandardOpenOption.WRITE);

        //Return the validation string of all locations
        return validationResults;
    }

    //Sorts just the locations which are VALID based on the criteria
    public List<List<Location>> listOfValidLocations(File validationResults) throws FileNotFoundException {

        Scanner myReader = new Scanner(validationResults);

        //Resulting list of all locations
        List<List<Location>> listOfLocations = new ArrayList<>();

        //Using for mapping the cities to the corresponding states
        HashMap<String, List<String>> map = new HashMap<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            //All words inside of one line
            List<String> words = Arrays.asList(data.split(";"));

            //Creating references just for clarification
            String state = words.get(0).substring(1);
            String city = words.get(1).substring(0, words.get(1).indexOf("="));
            String population = words.get(1).substring(words.get(1).indexOf("=") + 1);
            String isValid = words.get(2).substring(0, words.get(2).length() - 1);


            System.out.println("State: " + state + ", City:" + city + ", population: " + population);

            //If a line has VALID string at the end then add it to the
            //resulting list of Locations which we will present in our JSON
            if(isValid.equalsIgnoreCase("VALID")){
                List<String> list = new ArrayList<>();

                //Mapping all the cities to the corresponding state
                if(map.containsKey(state.toUpperCase())){
                    list = map.get(state.toUpperCase());
                }
                list.add(city + ":" + population);
                map.put(state.toUpperCase(), list);
            }
        }
        //Close the reader
        myReader.close();


        //Iterating over each key and value pair in a HashMap
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {

            //Extracting the state
            String key = entry.getKey();

            //Extracting the list of cities for that state
            List<String> value = entry.getValue();

            //Creating state instance
            Location state = new Location(key);

            //Creating country and population instance
            List<Location> temp = new ArrayList<>();

            //Adding first the state to the list
            temp.add(state);

            //Creating instances of countries and population and populating the location list
            for (String s : value) {
                temp.add(new Location(s.substring(0, s.indexOf(":")), s.substring(s.indexOf(":") + 1)));
            }

            //Adding the location list to the resulting List<List<Location>>
            listOfLocations.add(temp);
        }

        //Returning the list of just VALID locations
        return listOfLocations;
    }


}

