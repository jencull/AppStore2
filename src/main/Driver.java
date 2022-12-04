/**
 * App Store
 * @author Jennefer Cullinan. Student no 20096634
 * @version 1.0
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
                case 3 -> runReportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortApps();
                case 6 -> printRecommendedApps();
                case 7 -> appOfTheDay();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue \n");
            option = mainMenu();
        }
        exitApp();
    }

    private void appOfTheDay() {

        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("The app of the day is: " + appStoreAPI.randomApp());
        } else {
            System.out.println("There are no apps in the App Store.");
        }
    }

    private void printRecommendedApps() {

        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("The recommended apps are: " + "\t" + appStoreAPI.listAllRecommendedApps());
        } else {
            System.out.println("There are no apps in the App Store.");
        }
    }

    private void sortApps() {

        if (appStoreAPI.numberOfApps() > 0) {
            appStoreAPI.sortAppsByNameAscending();
            System.out.println("The apps are sorted by name in ascending order");
        } else {
            System.out.println("There are no apps in the App Store");
        }
    }

    private int reportsMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");

    }

    private void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runAppsOverview();
                case 2 -> runDevelopersOverview();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue \n");
            option = reportsMenu();
        }
    }

    private int developersOverview() {

        System.out.println("""
                 -------Developer Overview----------
                |   1) Number of apps by developer |
                |   2) All apps by a developer     |
                |   0) RETURN to main menu         |
                 -----------------------------------""");
        return ScannerInput.validNextInt("==>> ");

    }

    private void runDevelopersOverview() {
        int option = developersOverview();
        while (option != 0) {
            switch (option) {
                case 1 -> developerNumbers();
                case 2 -> developerName();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developersOverview();
        }
    }

    private void developerNumbers() {
        Developer developer = readValidDeveloperByName();

        if (developer != null) {

            System.out.println("There are " +
                    appStoreAPI.numberOfAppsByChosenDeveloper(developer)
                    + " in the App Store");
        } else {
            System.out.println("There are no developers at present ");
        }
    }

    private void developerName() {
        if (appStoreAPI.numberOfApps() > 0) {
            String name = ScannerInput.validNextLine("Please enter the developer name: ");

            if (developerAPI.isValidDeveloper(name)) {
                System.out.println("The apps for " + name + " are: "
                        + appStoreAPI.listAllAppsByChosenDeveloper(developerAPI.getDeveloperByName(name)));
            } else {
                System.out.println("This is not a valid developer name");
            }
        } else {
            System.out.println("There are no apps in the App Store");
        }
    }

    private int appsOverview() {

        System.out.println("""
                 -----------App Reports-------------
                |   1) List All Education Apps     |
                |   2) List All Productivity Apps  |
                |   3) List All Game Apps          |
                |   4) List All Apps               |         
                |   0) RETURN to main menu         |
                 -----------------------------------""");
        return ScannerInput.validNextInt("==>> ");

    }

    private void runAppsOverview() {
        int option = appsOverview();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(appStoreAPI.listAllEducationApps());
                case 2 -> System.out.println(appStoreAPI.listAllProductivityApps());
                case 3 -> System.out.println(appStoreAPI.listAllGameApps());
                case 4 -> System.out.println(appStoreAPI.listAllApps());

                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue \n");
            option = appsOverview();
        }
    }

    private int appMenu() {

        System.out.println("""
                 --------App Menu-------------
                |   1) Add an App            |
                |   2) Update App            |
                |   3) Delete App            |
                |   0) RETURN to main menu   |
                 -----------------------------""");
        return ScannerInput.validNextInt("==>> ");

    }

    private void runAppMenu() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runAddAppMenu();
                case 2 -> runUpdateAppMenu();
                case 3 -> runDeleteAppMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private int addAnApp() {

        System.out.println("""
                 -------Add an App-------
                |   1) Education App         |
                |   2) Productivity App      |
                |   3) Game App              |
                |   0) RETURN to main menu   |
                 ----------------------------
                """);
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAddAppMenu() {
        int option = addAnApp();

            while (option != 0) {
                switch (option) {
                    case 1 -> addEducationApp();
                    case 2 -> addProductivityApp();
                    case 3 -> addGameApp();
                    default -> System.out.println("Invalid option entered" + option);
                }
            ScannerInput.validNextLine("\n Press the enter key to continue \n");
            option = addAnApp();
        }
    }

    private void addEducationApp() {
        boolean isAdded;

        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
        int level = ScannerInput.validNextInt("Please enter the app level, 1-10: ");

        isAdded = appStoreAPI.addApp(new EducationApp(new Developer(developerName, developerWebsite),
                appName, appSize, appVersion, appCost, level));

        if (isAdded) {
            System.out.println("App has been added.");
        } else {
            System.out.println("App not added.");
        }
    }

    private void addProductivityApp() {
        boolean isAdded;

        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

        isAdded = appStoreAPI.addApp(new ProductivityApp(new Developer(developerName, developerWebsite),
                appName, appSize, appVersion, appCost));

        if (isAdded) {
            System.out.println("App has been added.");
        } else {
            System.out.println("App not added.");
        }
    }

    private void addGameApp() {
        boolean isAdded;

        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
        char isMultiplayer = ScannerInput.validNextChar("Multiplayer? Y/N ");

        isAdded = appStoreAPI.addApp(new GameApp(new Developer(developerName, developerWebsite),
                appName, appSize, appVersion, appCost, Utilities.YNtoBoolean(isMultiplayer)));

        if (isAdded) {
            System.out.println("App has been added.");
        } else {
            System.out.println("App not added.");
        }
    }

    private int updateAnApp() {

        System.out.println("""
                 -------Add an App-------
                |   1) Update Education App         |
                |   2) Update Productivity App      |
                |   3) Update Game App              |
                |   0) RETURN to main menu          |
                 ------------------------------------
                """);

        return ScannerInput.validNextInt("==>> ");
    }

    private void runUpdateAppMenu() {
        int option = updateAnApp();

        while (option != 0) {
            switch (option) {
                case 1 -> updateEducationApp();
                case 2 -> updateProductivityApp();
                case 3 -> updateGameApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue \n");
            option = updateAnApp();
        }

    }

    private void updateEducationApp() {
        boolean isUpdated = false;

        System.out.println(appStoreAPI.listAllEducationApps());
        int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
        int level = ScannerInput.validNextInt("Please enter the app level, 1-10: ");

        isUpdated = appStoreAPI.updateEducationApp(indexToUpdate, appName, appSize, appVersion, appCost, level);

        if (isUpdated) {
            System.out.println("App has been updated.");
        } else {
            System.out.println("App not been updated.");
        }
    }

    private void updateProductivityApp() {
        boolean isUpdated = false;

        System.out.println(appStoreAPI.listAllProductivityApps());
        int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

        isUpdated = appStoreAPI.updateProductivityApp(indexToUpdate, appName, appSize, appVersion, appCost);

        if (isUpdated) {
            System.out.println("App has been updated.");
        } else {
            System.out.println("App not been updated.");
        }
    }

    private void updateGameApp() {
        boolean isUpdated;

        System.out.println(appStoreAPI.listAllGameApps());
        int indexToUpdate = ScannerInput.validNextInt("Please enter the index number of the app to be updated: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size; ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
        char isMultiplayer = ScannerInput.validNextChar("Multiplayer? Y/N: ");

        isUpdated = appStoreAPI.updateGameApp(indexToUpdate, appName, appSize, appVersion, appCost, Utilities.YNtoBoolean(isMultiplayer));

        if (isUpdated) {
            System.out.println("App has been updated.");
        } else {
            System.out.println("App not been updated.");
        }
    }

    private int deleteApp() {

        System.out.println("""
                 -------Delete an App-------
                |   1) Delete Education App         |
                |   2) Delete Productivity App      |
                |   3) Delete Game App              |
                |   0) RETURN to main menu          |
                 ------------------------------------
                """);
        return ScannerInput.validNextInt("==>> ");
    }


    private void runDeleteAppMenu() {
        int option = deleteApp();
        while (option != 0) {
        switch (option) {
            case 1 -> deleteEducationApp();
            case 2 -> deleteProductivityApp();
            case 3 -> deleteGameApp();
            default -> System.out.println("Invalid option entered" + option);
        }
        ScannerInput.validNextLine("\n Press the enter key to continue \n");
        option = deleteApp();
    }

}

    private void deleteEducationApp() {

        System.out.println(appStoreAPI.listAllEducationApps());

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

    private void deleteProductivityApp() {
        System.out.println(appStoreAPI.listAllProductivityApps());

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

    private void deleteGameApp() {
        System.out.println(appStoreAPI.listAllGameApps());

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
                ScannerInput.validNextLine("\n Press the enter key to continue \n");
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
    //system does not auto update the app info when developer is updated. Apps listed
    // so that user can manually update the apps in the App Store

        private void updateDeveloper () {
            System.out.println(developerAPI.listDevelopers());

            System.out.println(appStoreAPI.listAllApps() + "\n");

            Developer developer = readValidDeveloperByName();

            if (developer != null) {

                String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
                if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite)) {
                    System.out.println("Developer Website Updated. Please amend apps with new details.");
                }
                else {
                    System.out.println("Developer Website NOT Updated");
                }
            } else
                System.out.println("Developer name is NOT valid");
        }
        //deleting a developer will not delete related apps. Info provided to allow user makes necessary changes
        private void deleteDeveloper () {
            System.out.println(developerAPI.listDevelopers());

            System.out.println(appStoreAPI.listAllApps() + "\n");

            String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
            if (developerAPI.removeDeveloper(developerName) != null) {
                System.out.println("Delete successful. Please amend apps to reflect developer changes");
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
                      3) Rating (all apps of that rating or above)
                      """);

            int option = ScannerInput.validNextInt("==>> ");
            switch (option) {

                case 1 -> searchAppsByName();
                case 2 -> searchAppsByDeveloper();
                case 3 -> searchAppsEqualOrAboveAStarRating();
                default -> System.out.println("Invalid option");
            }
        }

        private void searchAppsEqualOrAboveAStarRating () {

            int rating = ScannerInput.validNextInt(("Please enter the star rating: "));

            if (appStoreAPI.numberOfApps() > 0) {
                System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));
            }
            else {
                System.out.println("There are no apps in the App Store");
            }
        }
        private void searchAppsByDeveloper () {
        Developer developer = readValidDeveloperByName();

            if (appStoreAPI.numberOfApps() > 0) {

                    System.out.println("Apps by developer " + developer + "\n"
                        + appStoreAPI.listAllAppsByChosenDeveloper(developer));
        }
            else {
                System.out.println("There are no apps in the App Store");
            }
        }

        private void searchAppsByName() {
            String name = ScannerInput.validNextLine("Please enter the name of the app: ");

            if (appStoreAPI.numberOfApps() > 0) {

                if(appStoreAPI.isValidAppName(name)) {
                    System.out.println(appStoreAPI.getAppByName(name));
                } else {
                    System.out.println("No apps with the name " + name);
                }
            } else {
                System.out.println("There are no apps in the App Store");
            }
        }


        private void simulateRatings () {
            //simulate random ratings for all apps (to give data for recommended apps and reports etc).
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

