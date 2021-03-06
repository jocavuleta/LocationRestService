package com.main.croz.controller;

import com.main.croz.model.Location;
import com.main.croz.restservice.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class Controller {

    //Allows us to access the methods inside the locationService
    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public List<Location> locations() throws IOException {
        //Getting the validationResult output txt file
        File validationsResults = locationService.readAndValidateFile("src/main/java/fileToRead.txt");

        //Extracting the list of all VALID locations from the validationResult txt file
        List<Location> validLocations = locationService.listOfValidLocations(validationsResults);

        //returning the list
        return validLocations;
    }
}
