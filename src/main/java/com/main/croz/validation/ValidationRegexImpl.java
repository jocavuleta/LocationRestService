package com.main.croz.validation;

import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;

@Repository
public class ValidationRegexImpl implements Validation{
    @Override
    public boolean isAlpha(String line) {
        if(line.matches("(.*[a-z].*)(.*[A-Z].*)") || line.matches("(.*[A-Z].*)(.*[a-z].*)")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isDigit(String line) {
        if(line.matches("(.*\\d.*)"))
            return true;

        return false;
    }

    @Override
    public boolean isASCII(String line) {
        if(line.matches("\\A\\p{ASCII}*\\z")){
            System.out.println("Uso u regex true");
            return true;
        }
        return false;
    }
}
