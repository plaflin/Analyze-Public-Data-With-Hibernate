package com.teamtreehouse.worldbank.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// POJO to hold Country Data
@Entity
public class Country {
    @Id
    private String code;
    private String name;
    private Double internetUsers;
    private Double adultLiteracyRate;

    public Country() {}	// End default constructor

    public Country(String code, String name, Double internetUsers, Double adultLiteracyRate) {
        this.code = code;
        this.name = name;
        this.internetUsers = internetUsers;
        this.adultLiteracyRate = adultLiteracyRate;
    } // End Country(code, name, internetUsers, adultLiteracyRate)

    public Country(CountryBuilder builder) {
        this.code = builder.code;
        this.name = builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    } // End Country(builder)

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        this.internetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + ", internetUsers=" + internetUsers + ", adultLiteracyRate="
                + adultLiteracyRate + "]";
    }

    // Inner class to allow the use of the Builder model
    public static class CountryBuilder {
        private String code;
        private String name;
        private Double internetUsers;
        private Double adultLiteracyRate;

        public CountryBuilder(String code, String name) {
            this.code = code;
            this.name = name;
        } // End CountryBuilder(code, name)

        public CountryBuilder(String code, String name, Double internetUsers) {
            this.code = code;
            this.name = name;
            this.internetUsers = internetUsers;
        } // End CountryBuilder(code, name, internetUsers)

        public CountryBuilder(String code, String name, Double internetUsers, Double adultLiteracyRate ) {
            this.code = code;
            this.name = name;
            this.internetUsers = internetUsers;
            this.adultLiteracyRate = adultLiteracyRate;
        }  // End CountryBuilder(code, name, internetUsers, adultLiteracyRate)

        public Country build() {
            return new Country(this);
        }
    } // End CountryBuilder

} // End Country.java
