package com.teamtreehouse.worldbank.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.teamtreehouse.worldbank.model.Country;
import com.teamtreehouse.worldbank.validator.Validate;
import com.teamtreehouse.worldbank.dao.CountryDAO;
import com.teamtreehouse.worldbank.dao.CountryDAOImpl;

// Class to perform operations on data retrieved from the World Bank table
public class CountriesRepository {

    CountryDAO dao =  new CountryDAOImpl();

    // Outputs a fairly readable table to the console. Would probably look better in html/css
    public void showStatisticsTable(){
        System.out.printf("%-4S %-32S %-15S %-17S %n", "code", "name" ,"internet Users", "adult Literacy Rate");
        List<Country> countries = dao.fetchAllCountries();
        for(Country c : countries) {
            System.out.printf("%4s| ", c.getCode());
            System.out.printf("%32s| ", c.getName());
            if(c.getInternetUsers() != null)
                System.out.printf("%13.2f| ",c.getInternetUsers());
            else
                System.out.printf("%13S| ", "--");
            if(c.getAdultLiteracyRate() != null)
                System.out.printf("%18.2f| %n",c.getAdultLiteracyRate());
            else
                System.out.printf("%18S| %n","--");
        }
    } // End showStatisticsTable()

    // Gets all of the adult literacy rates from the table. Then outputs the max and min to console.
    public void getAdultLitRates() {
        Comparator<Country> literacyComparator = Comparator.comparingDouble(Country::getAdultLiteracyRate);
        List<Country> c = dao.fetchAllCountries();
        List<Country> adultLitRates = new ArrayList<>();
        for(Country country : c){
            if(country.getAdultLiteracyRate() != null) {
                adultLitRates.add(country);
            }
        }

        // Streams are so cool
        Country maxAdultLit = adultLitRates.stream().max(literacyComparator).get();
        Country minAdultLit = adultLitRates.stream().min(literacyComparator).get();

        // Output for the max and min. I like to output the max first to start on a high note.
        System.out.printf("The country with the highest reported adult literacy rate was %s with a reported %f literate citizens\n",
                maxAdultLit.getName(), maxAdultLit.getAdultLiteracyRate());
        System.out.printf("The country with the lowest reported Internet usage was %s with a reported %f users\n\n",
                minAdultLit.getName(), minAdultLit.getAdultLiteracyRate());

    } // End getAdultLitRates()

    // Gets all of the adult literacy rates from the table. Then outputs the max and min to console.
    public void getNetUseStats() {
        Comparator<Country> internetComparator = Comparator.comparingDouble(Country::getInternetUsers);
        List<Country> c = dao.fetchAllCountries();
        List<Country> netUsageRates = new ArrayList<>();
        for(Country country : c){
            if(country.getInternetUsers() != null) {
                netUsageRates.add(country);
            }
        }

        // Streams are so cool
        Country highestInternet = netUsageRates.stream().max(internetComparator).get();
        Country lowestInternet = netUsageRates.stream().min(internetComparator).get();

        // Output for the max and min. I like to output the max first to start on a high note.
        System.out.printf("The country with the highest reported Internet usage was %s with a reported %f users\n",
                highestInternet.getName(), highestInternet.getInternetUsers());
        System.out.printf("The country with the lowest reported Internet usage was %s with a reported %f users\n\n",
                lowestInternet.getName(), lowestInternet.getInternetUsers());

    }	 // End getNetUseStats()

    // Calculates the correlation coefficient between literacy and Internet usage
    public void getCorrelationCoefficient() {
        List<Country> c = dao.fetchAllCountries();
        List<Double> internet = new ArrayList<>();
        List<Double> literacy = new ArrayList<>();
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;
        int n;

        // This loop checks to see if the data is available in both columns to make arrays of the same length
        // Arrays being the same length is very important for the calculation
        for(Country country : c) {
            if((country.getAdultLiteracyRate() != null) && (country.getInternetUsers() != null)){
                internet.add(country.getInternetUsers());
                literacy.add(country.getAdultLiteracyRate());
            }
        }

        n = internet.size();

        for(int i = 0; i < n; i++){
            double x = internet.get(i).doubleValue();
            double y = literacy.get(i).doubleValue();

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // covariation
        double cov = sxy / n - sx * sy / n / n;

        // standard error of x
        double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);

        // standard error of y
        double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

        // correlation is just a normalized covariation
        double correlationCoefficient = cov / sigmax / sigmay;

        System.out.printf("The correlation coefficient for adult literacy and internet usage is %f%n",
                correlationCoefficient);
    } // End getCorrelationCoefficient()

    // Things change and this functions lets the user change country data
    public void updateCountryInfo(Scanner scanner) {
        Country needsUpdating = null;
        List<Country> countries = dao.fetchAllCountries();
        boolean approved, editMore = true;
        int choice;
        String option, changeStat;

        do {
            showStatisticsTable();
            System.out.println("\nWhich country's data would you like to update?");
            System.out.println("Please use the country code from the table above associated with the country.");
            option = scanner.nextLine().toUpperCase();
            for(Country c : countries){
                if(c.getCode().equalsIgnoreCase(option)){
                    needsUpdating = c;
                }
            }
            if(needsUpdating == null){
                System.out.println("You have entered an invalid country code.");
                approved = false;
            }
            else {
                System.out.println(needsUpdating.getName());
                approved = true;
            }
        } while(!approved);
        // End do-while

        do {
            System.out.printf("How would you like to update %S's information?%n", needsUpdating.getName());
            System.out.printf("Press 1 to change %S's name.%n", needsUpdating.getName());
            System.out.printf("Press 2 to change %S's country code.%n", needsUpdating.getName());
            System.out.printf("Press 3 to change %S's reported internet user percentage.%n", needsUpdating.getName());
            System.out.printf("Press 4 to change %S's reported adult literacy rate%n", needsUpdating.getName());
            System.out.printf("Press 0 to quit editing %S's information.%n", needsUpdating.getName());

            choice= scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1: // Change a country's name here
                    System.out.printf("Would you like to change %S name?%n", needsUpdating.getName());
                    System.out.println("Please press y to change the name.");
                    String changeName = scanner.nextLine();
                    if(changeName.equalsIgnoreCase("Y")){
                        System.out.println("What would you like to change the name to?");
                        String newName = scanner.nextLine();
                        System.out.printf("Do you really want to change the name to %S?%n", newName);
                        System.out.println("Please press y to change the name");
                        changeName = scanner.nextLine();
                        if(changeName.equalsIgnoreCase("Y")){
                            needsUpdating.setName(newName);
                        }
                        dao.update(needsUpdating);
                    }
                    else {
                        break;
                    }
                    break; // End case 1
                case 2: // Change a country's country code here
                    System.out.printf("Would you like to change %S country code?%n", needsUpdating.getName());
                    System.out.println("Please press y to change the country code.");
                    String changeCode = scanner.nextLine();
                    if(changeCode.equalsIgnoreCase("Y")){
                        System.out.println("What would you like to change the country code to?");
                        String newCode = scanner.nextLine();
                        System.out.printf("Do you really want to change the country code to %S?%n", newCode);
                        System.out.println("Please press y to change the country code");
                        changeCode = scanner.nextLine();
                        if(changeCode.equalsIgnoreCase("Y")){
                            needsUpdating.setCode(newCode);;
                        }
                        dao.update(needsUpdating);
                    }
                    else {
                        break;
                    }
                    break; // End case 2
                case 3: // Change country's Internet usage percentage here
                    Double newNetUserPercent;
                    System.out.printf("Would you like to change %S internet user percentage?%n", needsUpdating.getName());
                    System.out.println("Please press y to change the internet user percentage.");
                    changeStat = scanner.nextLine();
                    if(changeStat.equalsIgnoreCase("Y")){
                        System.out.println("Are you changing the data to not reported or removing outdated data with no replacement?");
                        System.out.println("Please press y for yes or any other key to enter a new internet user percentage");
                        option = scanner.nextLine();
                        if(option.equalsIgnoreCase("Y")){
                            newNetUserPercent = null;
                        }
                        else {
                            System.out.println("What would you like to change the internet user percentage to?");
                            newNetUserPercent = scanner.nextDouble();
                            scanner.nextLine();
                        }
                        System.out.printf("Do you really want to change the internet user percentage to %f?%n", newNetUserPercent);
                        System.out.println("Please press y to change the internet user percentage");
                        changeStat = scanner.nextLine();
                        if(changeStat.equalsIgnoreCase("Y")){
                            needsUpdating.setInternetUsers(newNetUserPercent);
                        }
                        dao.update(needsUpdating);
                    }
                    else {
                        break;
                    }
                    break; // End case 3
                case 4: // Change a country's adult literacy percentage here
                    Double newLitAdultPercent;
                    System.out.printf("Would you like to change %S adult literacy percentage?%n", needsUpdating.getName());
                    System.out.println("Please press y to change the adult literacy percentage.");
                    changeStat = scanner.nextLine();
                    if(changeStat.equalsIgnoreCase("Y")){
                        System.out.println("Are you changing the data to not reported or removing outdated data with no replacement?");
                        System.out.println("Please press y for yes or any other key to enter a new internet user percentage");
                        option = scanner.nextLine();
                        if(option.equalsIgnoreCase("Y")){
                            newLitAdultPercent = null;
                        }
                        else {
                            System.out.println("What would you like to change the adult literacy percentage to?");
                            newLitAdultPercent = scanner.nextDouble();
                            scanner.nextLine();
                        }
                        System.out.printf("Do you really want to change the adult literacy percentage to %f?%n", newLitAdultPercent);
                        System.out.println("Please press y to change the adult literacy percentage");
                        changeStat = scanner.nextLine();
                        if(changeStat.equalsIgnoreCase("Y")){
                            needsUpdating.setAdultLiteracyRate(newLitAdultPercent);
                        }
                        dao.update(needsUpdating);
                    }
                    else {
                        break;
                    }
                    break; // End case 4
                case 0: // The way out of the loop and back to the main menu
                    editMore = false;
                    break; // End case 0
                default: // A way to let the user know they made a mistake and returns to the local menu instead of the main
                    System.out.println("An error has occurred. Returing to the edit country menu");
            }  // End Switch

        } while (editMore);
        // End while loop
    } // End updateCountryInfo(scanner)

    // Sometimes countries no longer exist or are no longer recognized. This is for that case
    public void deleteCountryInfo(Scanner scanner) {
        Country needsDeleting = null;
        List<Country> countries = dao.fetchAllCountries();
        boolean approved = true;
        do {
            showStatisticsTable();
            System.out.println("\nWhich country's data would you like to delete?");
            System.out.println("Please use the country code from the table above associated with the country.");
            String choice = scanner.nextLine().toUpperCase();
            for(Country c : countries){
                if(c.getCode().equals(choice)){
                    needsDeleting = c;
                }
            }
            if(needsDeleting == null){
                System.out.println("You have entered an invalid country code.");
                approved = false;
            }
            else {
                System.out.println(needsDeleting.getName());
                approved = true;
            }
        } while(!approved);
        // End do-while

        System.out.printf("Do you really want to delete %S's entry from the database?%n", needsDeleting.getName());
        System.out.println("Please press y to delete from the database");
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("Y")){
            dao.delete(needsDeleting);
        }
        else {
            System.out.printf("You have chosen to not delete %S from the table%n", needsDeleting.getName());
            System.out.println("Returning to the main menu\n");
        }
    } // End deleteCountryInfo(scanner)

    // Sometimes new countries are formed or are formally recognized for the first time. This is for that case.
    public void addNewCountry(Scanner scanner) {
        Validate validator = new Validate();
        String newCountry, newCountryCode, choice;
        Double adultLitRate, netUsers;
        boolean approved = true;

        do {
            approved = true;
            System.out.println("What country would you like to add to the database?");
            newCountry = scanner.nextLine();
            approved = validator.validateCountryName(newCountry);
        } while(!approved);
        // End do-while

        do {
            approved = true;
            System.out.printf("What country code would you like %S to have?%n", newCountry);
            newCountryCode = scanner.nextLine().toUpperCase();
            approved = validator.validateCountryCode(newCountryCode);
        } while(!approved);
        // End do-while

        System.out.printf("Was the adult literacy rate for %s reported?%n", newCountry);
        System.out.println("Please press y for yes or any other key for no");
        choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("Y")){
            System.out.printf("What adult literacy rate was reported for %S?%n", newCountry);
            adultLitRate = scanner.nextDouble();
            scanner.nextLine();
            adultLitRate = validator.validateStatistic(adultLitRate);
        }
        else {
            adultLitRate = null;
        }

        System.out.printf("Was the Internet usage rate for %s reported?%n", newCountry);
        System.out.println("Please press y for yes or any other key for no");
        choice = scanner.nextLine();
        System.out.printf("What Internet usage rate was reported for %S?%n", newCountry);
        if(choice.equalsIgnoreCase("Y")){
            netUsers = scanner.nextDouble();
            scanner.nextLine();
            netUsers = validator.validateStatistic(netUsers);
        }
        else {
            netUsers = null;
        }

        Country country = new Country.CountryBuilder(newCountryCode, newCountry, netUsers, adultLitRate ).build();
        String id = dao.save(country);

        if(id != null) {
            System.out.println("You have successfully added a new country to the datbase!\n");
        }
    }  // End addNewCountry(scanner)
}
