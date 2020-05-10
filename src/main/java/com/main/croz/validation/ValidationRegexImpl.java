package com.main.croz.validation;

import org.springframework.stereotype.Repository;

@Repository
public class ValidationRegexImpl implements Validation{
    @Override
    public boolean isAlpha(String line) {
        if(line.matches("^[a-zA-Z]*$")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isDigit(String line) {
        if(line.matches(".*[^0-9].*"))
            return true;

        return false;
    }

    @Override
    public boolean isASCII(String line) {
        return false;
    }
}
