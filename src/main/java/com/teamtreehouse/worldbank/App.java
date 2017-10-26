package com.teamtreehouse.worldbank;

import java.util.Scanner;

import com.teamtreehouse.worldbank.data.CountriesRepository;

// Driver class for the program Analyze Public Data with Hibernate
public class App {

    static CountriesRepository repo = new CountriesRepository();

    public static void main(String[] args) {
        // The way the user will input information
        Scanner scanner = new Scanner(System.in);
        int working = 0;

        // I like programs with greetings. So, I put one in.
        System.out.println("Welcome to World Bank Statistical Program");

        // Using a do-while because we want the menu to show up at least once even if they want to exit right away
        do {

            working = menu(scanner);

        } while (working != 0);
        // End do-while

        scanner.close();
        System.exit(0);
    } // End main()

    // Lets the user know what they can do then directs them to the appropriate function
    private static int menu(Scanner scanner) {

        int choice = 0;
        menuOptions();
        System.out.println("What would you like to do today?");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: // Shows complete table of statistics
                repo.showStatisticsTable();
                break;
            case 2: // Shows the maximum and minimum reported literacy rates
                repo.getAdultLitRates();
                break;
            case 3: // Shows the maximum and minimum reported Internet usage rates
                repo.getNetUseStats();
                break;
            case 4: // Gets the correlation coefficient between literacy and Internet usage
                repo.getCorrelationCoefficient();
                break;
            case 5: // Allows the user to update a country
                repo.updateCountryInfo(scanner);
                break;
            case 6: // Allows the user to delete a country
                repo.deleteCountryInfo(scanner);
                break;
            case 7: // Allows the user to add a new country
                repo.addNewCountry(scanner);
                break;
            case 0: // Allows the user to end the program
                System.out.println("Have a nice day!");
                break;
            default: // Lets the user know they did something wrong before returning to the main menu
                System.out.println("An error has occurred. Returning to the menu");
        }  // End switch

        return choice;
    } // End menu()

    // Main menu options to let the user know what they can do with the program
    private static void menuOptions() {
        System.out.println("Press 1 to see the complete table of statistics.");
        System.out.println("Press 2 to see the maximum and minimum reported adult literacy rates.");
        System.out.println("Press 3 to see the maximum and minimum reported Internet usage.");
        System.out.println("Press 4 to see the correlation coefficient between literacy and Internet usage.");
        System.out.println("Press 5 to update a country's information.");
        System.out.println("Press 6 to remove a country from the table.");
        System.out.println("Press 7 to add a country to the table.");
        System.out.println("Press 0 to quit the program.");
    } // End menuOptions()

} // End App.java
