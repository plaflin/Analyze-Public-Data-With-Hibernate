package com.teamtreehouse.worldbank.dao;

import java.util.List;

import com.teamtreehouse.worldbank.model.Country;

// Interface to perform CRUD operations on World Bank table
public interface CountryDAO {
    String save(Country country);
    List<Country> fetchAllCountries();
    void update(Country country);
    void delete(Country country);
} // End CountryDAO.java
