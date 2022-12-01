package controllers;

import models.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private Developer developerJen = new Developer("Jen", "www.jen.com/SETU");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0, 1);

        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5, 2.99, 11);

        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00, 0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0);

        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99);

        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99);

        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        gameAppBelowBoundary = new GameApp(developerEAGames, "Tetris", 1, 1.0, 0.0, false);

        gameAppOnBoundary = new GameApp(developerKoolGames, "CookOff", 1000, 2.0, 1.99, true);

        gameAppAboveBoundary = new GameApp(developerEAGames, "Empires", 1001, 3.5, 2.99, false);

        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0, -1.00, true);

        //not included - edAppOnBoundary, edAppInvalidData, prodAppBelowBoundary, gameAppBelowBoundary, gameAppInvalidData.
        appStore.addApp(edAppBelowBoundary);
        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);
        appStore.addApp(gameAppOnBoundary);

    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {

        @Test
        void gettingAppListReturnsList() {
            List<App> testApps = new ArrayList<>();

            testApps.add(edAppBelowBoundary);
            testApps.add(prodAppOnBoundary);
            testApps.add(gameAppAboveBoundary);
            testApps.add(prodAppBelowBoundary);
            testApps.add(edAppAboveBoundary);
            testApps.add(prodAppInvalidData);
            testApps.add(gameAppOnBoundary);
            assertEquals(testApps, appStore.getApps());

            assertEquals(new ArrayList<App>(), emptyAppStore.getApps());
        }

        @Test
        void settingAppListReplacesList() {
            List<App> testApps = new ArrayList<>();

            testApps.add(edAppBelowBoundary);
            testApps.add(prodAppOnBoundary);
            testApps.add(gameAppOnBoundary);

            assertEquals(7, appStore.numberOfApps());
            appStore.setApps((ArrayList<App>) testApps);
            assertEquals(testApps, appStore.getApps());
            assertEquals(3, appStore.numberOfApps());
        }
    }

    @Nested
    class CRUDMethods {

        @Test
        void addingAnAppAddsToArrayList() {
            assertEquals(7, appStore.numberOfApps());

            //test education app added
            assertTrue(appStore.addApp(edAppOnBoundary));
            assertEquals(edAppOnBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));

            //test productivity app added
            assertTrue(appStore.addApp(prodAppBelowBoundary));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));

            //test game app added
            assertTrue(appStore.addApp(gameAppBelowBoundary));
            assertEquals(gameAppBelowBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));
        }

        @Test
        void deleteAppByIndexReturnsNullWhenArrayIsEmpty() {
            assertNull(emptyAppStore.deleteAppByIndex(0));
            assertNull(appStore.deleteAppByIndex(-1));
            assertNull(appStore.deleteAppByIndex((appStore.numberOfApps())));
        }

        @Test
        void deleteAppByIndexDeletesThenReturnsDeletedObject() {
            //delete app at start of arrayList
            assertEquals(7, appStore.numberOfApps());
            assertEquals(edAppBelowBoundary, appStore.deleteAppByIndex(0));
            assertEquals(6, appStore.numberOfApps());

            //delete app at end of arrayList
            assertEquals(6, appStore.numberOfApps());
            assertEquals(gameAppOnBoundary, appStore.deleteAppByIndex(5));
            assertEquals(5, appStore.numberOfApps());
        }

        @Test
        void updatingAnAppThatDoesNotExistReturnsFalse() {
            //testing empty app store
            assertFalse(emptyAppStore.updateGameApp(0, "update", 22, 2.0, 2.99, true));

            //testing each type of app update
            assertFalse(appStore.updateGameApp(8, "update", 22, 2.0, 2.99, true));
            assertFalse(appStore.updateProductivityApp(12, "update", 22, 2.0, 2.99));
            assertFalse(appStore.updateEducationApp(-1, "update", 22, 2.0, 2.99, 5));
        }

        @Test
        void updatingAGameAppThatExistsReturnsTrueAndUpdates() {
            //check game app exists at index 6 & verify
            GameApp game = (GameApp) appStore.getAppByIndex(6);
            assertEquals(gameAppOnBoundary, game);

            //update game app at index 6 and check the updates
            assertTrue(appStore.updateGameApp(6, "Twister", 13, 2.0, 3.99, false));
            GameApp newGame = (GameApp) appStore.getAppByIndex(6);
            assertEquals("Twister", newGame.getAppName());
            assertEquals(13, newGame.getAppSize());
            assertEquals(2.0, newGame.getAppVersion());
            assertEquals(3.99, newGame.getAppCost());
            assertEquals(false, newGame.isMultiplayer());
        }

        @Test
        void updatingAnEducationAppThatExistsReturnsTrueAndUpdates() {
            //check education app exists at index 0 & verify
            EducationApp edu = (EducationApp) appStore.getAppByIndex(0);
            assertEquals(edAppBelowBoundary, edu);

            //update game app at index 6 and check the updates
            assertTrue(appStore.updateEducationApp(0, "Learning Java", 13, 2.0, 3.99, 3));
            EducationApp edu2 = (EducationApp) appStore.getAppByIndex(0);
            assertEquals("Learning Java", edu2.getAppName());
            assertEquals(13, edu2.getAppSize());
            assertEquals(2.0, edu2.getAppVersion());
            assertEquals(3.99, edu2.getAppCost());
            assertEquals(3, edu2.getLevel());
        }

        @Test
        void updatingAProductivityAppThatExistsReturnsTrueAndUpdates() {
            //check productivity app exists at index 3 & verify
            ProductivityApp prod = (ProductivityApp) appStore.getAppByIndex(3);
            assertEquals(prodAppBelowBoundary, prod);

            //update game app at index 6 and check the updates
            assertTrue(appStore.updateProductivityApp(3, "TickAll", 13, 2.0, 3.99));
            ProductivityApp prod2 = (ProductivityApp) appStore.getAppByIndex(3);
            assertEquals("TickAll", prod2.getAppName());
            assertEquals(13, prod2.getAppSize());
            assertEquals(2.0, prod2.getAppVersion());
            assertEquals(3.99, prod2.getAppCost());
        }
    }

    @Nested
    class ReportingMethods {

        @Test
        void listAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test //printing all -1 Wedo
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            System.out.println(apps);
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(7, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test //not printing, all -1 WeDo
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());
            System.out.println(appStore.listAllApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5, 4));
            appStore.addApp(setupEducationAppWithRating(3, 4));
            appStore.addApp(setupProductivityAppWithRating(3, 4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();

            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }

        @Test
            //check
        void ListSummaryOfAllAppsReturnsNoAppsWhenArrayListEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listSummaryOfAllApps().toLowerCase().contains("no apps"));
        }

        @Test // printing properly
        void ListSummaryOfAllAppsReturnsAppWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listSummaryOfAllApps();
            System.out.println(apps);

            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));

        }

        @Test
        void listAllGamesAppsReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllGameApps().toLowerCase().contains("no game apps"));

        }

        @Test //printing fine
        void listAllGamesAppsReturnsAppsWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllGameApps();

            //checks for objects in the string
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listAllEducationAppsReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllEducationApps().toLowerCase().contains("no education apps"));
        }

        @Test //printing fine
        void listAllEducationAppsReturnsAppsWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllEducationApps();
            System.out.println(apps);
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("EV3"));
        }

        @Test
        void listAllProductivityAppsReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllProductivityApps().toLowerCase().contains("no productivity apps"));
        }

        @Test //printing fine
        void listAllProductivityAppsReturnsAppsWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllProductivityApps();

            //checks for objects in the string
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("NoteKeeper"));
        }

        @Test
        void ListAllAppsByNameReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsByName("outlook").toLowerCase().contains("no apps for "));
        }

        @Test //not printing out ok, returns no apps for ?
        void ListAllAppsByNameReturnsAppsWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());
            System.out.println(appStore.listAllApps());

            String apps = appStore.listAllAppsByName("WeDo");
            String apps1 = appStore.listAllAppsByName("Outlook");
            String apps2 = appStore.listAllAppsByName("Empires");
            System.out.println(apps);
            System.out.println(apps1);
            System.out.println(apps2);

            assertTrue(apps.contains("WeDo"));
            assertTrue(apps1.contains("Outlook"));
            assertTrue(apps2.contains("Empires"));
        }

        @Test
        void ListAllAppsAboveOrEqualAStarRatingReturnsNoAppsWhenArrayIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsAboveOrEqualAGivenStarRating(2).toLowerCase().contains("no apps have a rating of"));
        }

        @Test // not printing out all 10 apps but prints the 7 alright
        void ListAllAppsAboveOrEqualAStarRatingReturnsAppsWhenArrayListHasApps() {
            assertEquals(7, appStore.numberOfApps());


            appStore.addApp(setupGameAppWithRating(5, 4));
            appStore.addApp(setupEducationAppWithRating(3, 4));
            appStore.addApp(setupProductivityAppWithRating(3, 4));
            assertEquals(10, appStore.numberOfApps());
            System.out.println(appStore.numberOfApps());

            String appsWithRating = appStore.listAllAppsAboveOrEqualAGivenStarRating(1);
            System.out.println(appsWithRating);
            //checks names of apps
            assertTrue(appsWithRating.contains("MazeRunner"));
            assertTrue(appsWithRating.contains("WeDo"));
            assertTrue(appsWithRating.contains("Evernote"));
        }
        @Test
        void ListAllAppsByDeveloperReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsByChosenDeveloper(developerLego).toLowerCase().contains("no apps for "));
        }

        @Test
        void ListAllAppsByDeveloperReturnsNoAppsFoundWhenArrayListHasNoMatchingAoos() {
            assertEquals(7, appStore.numberOfApps());

            String searchApps = appStore.listAllAppsByChosenDeveloper(developerLego);
            assertFalse(searchApps.contains("www.microsoft.com"));

        }

        @Test
        void ListAllAppsByDeveloperReturnsAppsWhenArrayListHasAppsMatching() {
            assertEquals(7, appStore.numberOfApps());

            String searchApps = appStore.listAllAppsByChosenDeveloper(developerLego);
            assertTrue(searchApps.contains("www.lego.com"));

            String searchApps1 = appStore.listAllAppsByChosenDeveloper(developerApple);
            assertTrue(searchApps1.contains("www.apple.com"));

            String searchApps2 = appStore.listAllAppsByChosenDeveloper(developerMicrosoft);
            assertTrue(searchApps2.contains("www.microsoft.com"));
        }
    }

    @Nested
    class CountingMethods {

        @Test
        void NumberOfAppsByChosenDeveloperReturnsNoAppsWhenArrayListEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertEquals(0, emptyAppStore.numberOfAppsByChosenDeveloper(developerLego));
        }

        @Test
        void NumberOfAppsByChosenDeveloperReturnsNoAppsWhenNoMatchingAppsByDeveloper() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(0, appStore.numberOfAppsByChosenDeveloper(developerJen));
        }

        @Test
        void NumberOfAppsByChosenDeveloperReturnsAppsWhenMatchingAppsByDeveloper() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(1, appStore.numberOfAppsByChosenDeveloper(developerEAGames));
            assertEquals(1, appStore.numberOfAppsByChosenDeveloper(developerApple));
        }
    }

    @Nested
    class SearchingMethods {

        @Test
        void getAppByIndexReturnsAppWhenIndexIsValid() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(6));
        }

        @Test
        void getAppByIndexReturnsNullWhenIndexIsInvalid() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertNull(emptyAppStore.getAppByIndex(0));

            assertEquals(7, appStore.numberOfApps());
            assertNull(appStore.getAppByIndex(-1));
            assertNull(appStore.getAppByIndex(11));
        }

        @Test
        void getAppByIndexReturnsNullWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertNull(emptyAppStore.getAppByIndex(0));

            assertEquals(7, appStore.numberOfApps());
            assertNull(appStore.getAppByIndex(-1));
            assertNull(appStore.getAppByIndex(11));
        }

        @Test
        void getAppByNameReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertNull(emptyAppStore.getAppByName("MazeRunner"));
            assertNull(emptyAppStore.getAppByName("made up name"));
        }

        @Test
        void getAppByNameReturnsNoAppsWhenArrayListHasNoMatchingName() {
            assertEquals(7, appStore.numberOfApps());

            assertNull(appStore.getAppByName("made up name"));
        }

        @Test
        void getAppByNameReturnsAppFoundWhenArrayListHasMatchingName() {
            assertEquals(7, appStore.numberOfApps());
            System.out.println(appStore.listAllApps());

            assertEquals(gameAppOnBoundary,appStore.getAppByName("CookOff"));

        }
    }

    @Nested
    class SortingMethods {

        @Test
        void randomAppReturnsNullWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());

            assertNull(emptyAppStore.randomApp());
        }

        @Test
        void randomAppReturnsAppWithinCorrectArraySize() {
            assertEquals(7, appStore.numberOfApps());

            App app = appStore.randomApp();

            assertTrue(appStore.getApps().contains(app));
        }

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(7, appStore.numberOfApps());
            //order apps have been added
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(3));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(5));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(6));

            appStore.sortAppsByNameAscending();
            //new order
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(0));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(3));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(5));
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(6));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }
    }


    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1,
                1.0, 1.00, true);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1,
                1.0, 1.99);

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

}

