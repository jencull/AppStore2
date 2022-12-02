/**
 * App Store
 * @author Jennefer Cullinan. Student no 20096634
 * @version 1.0
 * Github - AppStore https://github.com/jencull/AppStore
 * Github2 - AppStore2 https://github.com/jencull/AppStore2
 *
 */

package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {


    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu();
                //case 3 -> // TODO run the Reports Menu and the associated methods (your design here)
                case 4 -> searchAppsBySpecificCriteria();
                //case 5 -> // TODO Sort Apps by Name
                //case 6 -> // TODO print the recommended apps
                //case 7 -> // TODO print the random app of the day
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private int appMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add an App            |
                |   2) Update App            |
                |   3) Delete App            |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");

    }
    private void runAppMenu () {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addAnApp();
                case 2 -> updateAnApp();
                case 3 -> deleteApp();
                case 4 -> mainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private void addAnApp() {
        boolean isAdded = false;

            int option = ScannerInput.validNextInt("""
                     -------Add an App-------
                    |   1) Education App         |
                    |   2) Productivity App      |
                    |   3) Game App              |
                    |   0) RETURN to main menu   |
                     ----------------------------
                    ==>>""");


                switch (option) {
                    case 1 -> { // education app
                        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
                        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
                        String appName = ScannerInput.validNextLine("Please enter the app name: ");
                        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                        int level = ScannerInput.validNextInt("Please enter the app level, 1-10: ");

                        isAdded = appStoreAPI.addApp(new EducationApp(new Developer(developerName, developerWebsite),
                                                    appName, appSize, appVersion, appCost, level));
                    }
                    case 2 -> { //productivity app
                        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
                        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
                        String appName = ScannerInput.validNextLine("Please enter the app name: ");
                        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

                        isAdded = appStoreAPI.addApp(new ProductivityApp(new Developer(developerName, developerWebsite),
                                                    appName, appSize, appVersion, appCost));
                    }
                    case 3 -> { // game app
                        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
                        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
                        String appName = ScannerInput.validNextLine("Please enter the app name: ");
                        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                        char isMultiplayer = ScannerInput.validNextChar("Multiplayer? Y/N");

                        isAdded = appStoreAPI.addApp(new GameApp(new Developer(developerName, developerWebsite),
                                                    appName, appSize, appVersion, appCost, Utilities.YNtoBoolean(isMultiplayer)));
                    }
                    case 4 -> runMainMenu(); // 0 or 4?

                    default -> System.out.println("Invalid option entered" + option);
                }
                if (isAdded) {
                    System.out.println("App has been added.");
                } else {
                    System.out.println("App not added.");
                }
                ScannerInput.validNextLine("\n Press the enter key to continue");
            }


    private void updateAnApp() {
        boolean isUpdated = false;

        int option = ScannerInput.validNextInt("""
                     -------Add an App-------
                    |   1) Update Education App         |
                    |   2) Update Productivity App      |
                    |   3) Update Game App              |
                    |   0) RETURN to main menu          |
                     ------------------------------------
                    ==>>""");


            switch (option) {
                case 1 -> { // education app
                    appStoreAPI.listAllEducationApps();
                    int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                    double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                    double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                    int level = ScannerInput.validNextInt("Please enter the app level, 1-10: ");

                    isUpdated = appStoreAPI.updateEducationApp(indexToUpdate, appName, appSize, appVersion, appCost, level);
                }
                case 2 -> { // productivity app
                    appStoreAPI.listAllProductivityApps();
                    int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                    double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                    double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

                    isUpdated = appStoreAPI.updateProductivityApp(indexToUpdate, appName, appSize, appVersion, appCost);
                }
                case 3 -> { // game app
                    appStoreAPI.listAllGameApps();
                    int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
                    double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                    double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                    char isMultiplayer = ScannerInput.validNextChar("Multiplayer? Y/N: ");

                    isUpdated = appStoreAPI.updateGameApp(indexToUpdate, appName, appSize, appVersion, appCost, Utilities.YNtoBoolean(isMultiplayer));
                }
                case 0 -> runMainMenu(); //0 or 4? while (choice !=0)

                default -> System.out.println("Invalid option entered" + option);
            }
            if (isUpdated) {
                System.out.println("App has been updated.");
            } else {
                System.out.println("App not been updated.");
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
        }

    private void deleteApp() {
        boolean isDeleted = false;

        int option = ScannerInput.validNextInt("""
                     -------Add an App-------
                    |   1) Delete Education App         |
                    |   2) Delete Productivity App      |
                    |   3) Delete Game App              |
                    |   0) RETURN to main menu          |
                     ------------------------------------
                    ==>>""");


        switch (option) {
            case 1 -> { // education app
                appStoreAPI.listAllEducationApps();

                if (appStoreAPI.numberOfApps() > 0) {
                    int indexToDelete = ScannerInput.validNextInt("Please enter the index number of the app to be deleted: ");
                    App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);

                    if (appToDelete != null) {
                        System.out.println("App is deleted. Deleted app: " + appToDelete.appSummary());
                    }
                    else {
                        System.out.println("App has not been deleted.");
                    }
                }
            }
            case 2 -> { // productivity app
                appStoreAPI.listAllProductivityApps();

                if (appStoreAPI.numberOfApps() > 0) {
                    int indexToDelete = ScannerInput.validNextInt("Please enter the index number of the app to be deleted: ");
                    App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);

                    if (appToDelete != null) {
                        System.out.println("App is deleted. Deleted app: " + appToDelete.appSummary());
                    }
                    else {
                        System.out.println("App has not been deleted.");
                    }
                }

            }
            case 3 -> { // game app
                appStoreAPI.listAllGameApps();

                if (appStoreAPI.numberOfApps() > 0) {
                    int indexToDelete = ScannerInput.validNextInt("Please enter the index number of the app to be deleted: ");
                    App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);

                    if (appToDelete != null) {
                        System.out.println("App is deleted. Deleted app: " + appToDelete.appSummary());
                    } else {
                        System.out.println("App has not been deleted.");
                    }
                }
            }
            case 0 -> runMainMenu(); //0 or 4? while (choice !=0)

            default -> System.out.println("Invalid option entered" + option);
        }
        ScannerInput.validNextLine("\n Press the enter key to continue");
    }



        private void exitApp () {
            saveAllData();
            System.out.println("Exiting....");
            System.exit(0);
        }

        //--------------------------------------------------
        //  Developer Management - Menu Items
        //--------------------------------------------------
        private int developerMenu () {
            System.out.println("""
                     -------Developer Menu-------
                    |   1) Add a developer       |
                    |   2) List developer        |
                    |   3) Update developer      |
                    |   4) Delete developer      |
                    |   0) RETURN to main menu   |
                     ----------------------------""");
            return ScannerInput.validNextInt("==>> ");
        }

        private void runDeveloperMenu () {
            int option = developerMenu();
            while (option != 0) {
                switch (option) {
                    case 1 -> addDeveloper();
                    case 2 -> System.out.println(developerAPI.listDevelopers());
                    case 3 -> updateDeveloper();
                    case 4 -> deleteDeveloper();
                    default -> System.out.println("Invalid option entered" + option);
                }
                ScannerInput.validNextLine("\n Press the enter key to continue");
                option = developerMenu();
            }
        }

        private void addDeveloper () {
            String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
            String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

            if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
                System.out.println("Add successful");
            } else {
                System.out.println("Add not successful");
            }
        }

        private void updateDeveloper () {
            System.out.println(developerAPI.listDevelopers());
            Developer developer = readValidDeveloperByName();
            if (developer != null) {
                String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
                if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                    System.out.println("Developer Website Updated");
                else
                    System.out.println("Developer Website NOT Updated");
            } else
                System.out.println("Developer name is NOT valid");
        }

        private void deleteDeveloper () {
            String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
            if (developerAPI.removeDeveloper(developerName) != null) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete not successful");
            }
        }

        private Developer readValidDeveloperByName () {
            String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
            if (developerAPI.isValidDeveloper(developerName)) {
                return developerAPI.getDeveloperByName(developerName);
            } else {
                return null;
            }
        }

        private void searchAppsBySpecificCriteria () {
            System.out.println("""
                    What criteria would you like to search apps by:
                      1) App Name
                      2) Developer Name
                      3) Rating (all apps of that rating or above)""");
            int option = ScannerInput.validNextInt("==>> ");
            switch (option) {

                case 1 -> searchAppsByName();
                case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
                case 3 -> searchAppsEqualOrAboveAStarRating();
                default -> System.out.println("Invalid option");
            }
        }


        private void simulateRatings () {
            simulate random ratings for all apps (to give data for recommended apps and reports etc).
            if (appStoreAPI.numberOfApps() > 0) {
                System.out.println("Simulating ratings...");
                appStoreAPI.simulateRatings();
                System.out.println(appStoreAPI.listSummaryOfAllApps());
            } else {
                System.out.println("No apps");
            }
        }

        //--------------------------------------------------
        //  Persistence Menu Items
        //--------------------------------------------------

        private void saveAllData () {

            try {
                System.out.println("Saving to file: " + appStoreAPI.fileName());
                appStoreAPI.save();
            }
            catch (Exception e) {
                System.err.println("Error writing to file: " + e);
            }

            try {
                System.out.println("Saving to file: " + developerAPI.fileName());
                developerAPI.save();
            }
            catch (Exception e) {
                System.err.println("Error writing to file: " + e);
            }
        }

        private void loadAllData() {

            try {
                System.out.println("Loading from file: " + appStoreAPI.fileName());
                appStoreAPI.load();
            } catch (Exception e)
            {
                System.err.println("Error reading from file: " + e);
            }

            try {
                System.out.println("Loading from file: " + developerAPI.fileName());
                developerAPI.load();
            } catch (Exception e) {
                System.err.println("Error reading from file: " + e);
            }
        }
    }

