package com.main.croz.validation;

import org.springframework.stereotype.Repository;

@Repository
public class ValidationRegexImpl implements Validation{
    @Override
    public boolean isAlpha(String line) {
        if(line.matches("(.*[a-z].*)(.*[A-Z].*)")){
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
            return true;
        }
        return false;
    }
}
