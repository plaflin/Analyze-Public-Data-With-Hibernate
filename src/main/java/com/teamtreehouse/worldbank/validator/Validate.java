package com.teamtreehouse.worldbank.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teamtreehouse.worldbank.model.Country;
import com.teamtreehouse.worldbank.dao.CountryDAO;
import com.teamtreehouse.worldbank.dao.CountryDAOImpl;


// Class to do some minor validation of the input
public class Validate {

    CountryDAO dao = new CountryDAOImpl();

    // Instructions gave some formatting guidelines for country codes
    // This function keeps the input to those constraints
    public boolean validateCountryCode(String code){
        boolean approved = true;
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        List<Country> countries = dao.fetchAllCountries();

        if(code.length() == 0){
            System.out.println("I'm sorry but countries must have a country code. Please enter a code");
            approved = false;
        }

        if(code.length() > 3){
            System.out.println("I'm sorry but the code you have chosen for your country is too long. Please enter a code");
            approved = false;
        }

        for(Country c : countries) {
            if(c.getCode().equalsIgnoreCase(code)){
                System.out.println("I'm sorry but that country code is already in use. Please enter a code");
                approved = false;
            }
        }

        if(!matcher.matches()){
            System.out.println("I'm sorry but you can only use letters in the country code. Please enter a code");
            approved = false;
        }

        return approved;
    } // End validateCountryCode(code)

    // Instructions gave some formatting guidelines for country names
    // This function keeps the input to those constraints
    public boolean validateCountryName(String name){
        boolean approved = true;
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        List<Country> countries = dao.fetchAllCountries();

        if(name.length() == 0){
            System.out.println("Countries must have a name. Please enter a name");
            approved = false;
        }

        if(name.length() > 32){
            System.out.println("The name you have chosen for your country is too long. Please enter a name");
            approved = false;
        }

        for(Country c : countries) {
            if(c.getName().equalsIgnoreCase(name)){
                System.out.println("I'm sorry but that name is already in use. Please enter a name");
                approved = false;
            }
        }

        if(!matcher.matches()){
            System.out.println("I'm sorry but you can only use letters in the name. Please enter a name");
            approved = false;
        }

        return approved;
    } // End validateCountryName(name)

    // Instructions gave some formatting guidelines for statistics
    // This function keeps the input to those constraints
    public Double validateStatistic(Double stat) {
        Double truncatedDouble;
        if(stat == null){
            truncatedDouble = null;
        }
        else {
            truncatedDouble = BigDecimal.valueOf(stat)
                    .setScale(8, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        return truncatedDouble;
    } // End validateStatistic(stat)

} // End Validate.java
