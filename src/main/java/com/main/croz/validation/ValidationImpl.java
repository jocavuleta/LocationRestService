package com.main.croz.validation;

import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;

@Repository
public class ValidationImpl implements Validation{

    @Override
    public boolean isAlpha(String line) {
        boolean upper = false, lower = false;

        for(char c : line.toCharArray()){
            if(upper && lower)
                return true;
            else if(Character.isUpperCase(c))
                upper = true;
            else if(Character.isLowerCase(c))
                lower = true;
        }
        return false;
    }

    @Override
    public boolean isDigit(String line) {

        for(char c : line.toCharArray()){
            if(Character.isDigit(c))
                return true;
        }
        return false;
    }

    @Override
    public boolean isASCII(String line) {
        boolean isASCII = true;
        for (int i = 0; i < line.length(); i++) {
            int c = line.charAt(i);
            if (c > 0x7F) {
                isASCII = false;
                break;
            }
        }
        return isASCII;
    }

}
