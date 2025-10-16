package org.andrewfroze.tests;

import io.restassured.response.Response;
import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.framework.enums.Gender;
import org.andrewfroze.framework.enums.UserRole;
import org.andrewfroze.framework.models.response.ExtendedPlayerDto;
import org.andrewfroze.framework.utils.PropertyReader;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

public abstract class BaseTest {

    private static final PropertyReader testDataReader = new PropertyReader("test-data.properties");
    protected final String supervisorEditor = testDataReader.get("supervisor.editor", "supervisor");
    protected final String adminEditor = testDataReader.get("admin.editor", "admin");

    @BeforeSuite
    @Parameters("threads")
    public void setupThreads(@Optional("1") String threads) {
        System.setProperty("testng.thread.count", threads);
    }

    @DataProvider(name = "validAges")
    protected Object[][] getValidAges() {
        return new Object[][] {
                {16},
                {40},
                // depends on requirements should we include 60 on allowed ages or not (usually upper boarder in ranges is not included)
                {59}
        };
    }

    @DataProvider(name = "invalidAges")
    protected Object[][] getInvalidAges() {
        return new Object[][] {
                {15},
                // also depending on requirements should 60 be included in allowed age range or not (here not included)
                {60}
        };
    }

    protected void validateStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    protected void validateStatusCode(Response response, int expectedStatusCode, String messagePostfix) {
        assertEquals(response.statusCode(), expectedStatusCode,
                "Invalid status code %s.".formatted(messagePostfix));
    }

    protected void validateStatusCode(SoftAssert softAssert, Response response, int expectedStatusCode, String messagePostfix) {
        softAssert.assertEquals(response.statusCode(), expectedStatusCode,
                "Invalid status code %s.".formatted(messagePostfix));
    }

    protected void validateStatusCode(SoftAssert softAssert, Response response, int expectedStatusCode) {
        validateStatusCode(softAssert, response, expectedStatusCode, "");
    }

    protected void validateUser(SoftAssert softAssert, Response response, String gender, String userRole, String login, String password, String screenName, Integer age, Boolean verifyPassword) {
        var responseDto = response.as(ExtendedPlayerDto.class);
        softAssert.assertNotNull(responseDto.getId(), "Id is not correct.");
        validateUserDetails(softAssert, responseDto, login, password, screenName, gender, age, userRole,
                verifyPassword,
                "in user create response");

        if (responseDto.getId() != null ) {
            var playerController = new PlayerController();
            var createdPlayer = playerController.getPlayerById(responseDto.getId());
            validateStatusCode(softAssert, response, 200,
                    ": trying to get just created user by id");
            validateUserDetails(softAssert, createdPlayer.as(ExtendedPlayerDto.class),
                    login, password, screenName, gender, age, userRole,
                    true,
                    "in getPlayerById response");
        }
    }

    protected static void validateUserDetails(SoftAssert softAssert,
                                            ExtendedPlayerDto responseDto,
                                            String login,
                                            String password,
                                            String screenName,
                                            String gender,
                                            Integer age,
                                            String userRole,
                                            Boolean verifyPassword,
                                            String messagePostfix) {
        softAssert.assertEquals(responseDto.getLogin(), login, "Login is not correct %s."
                .formatted(messagePostfix));
        if (verifyPassword) {
            softAssert.assertEquals(responseDto.getPassword(), password, "Password is not correct %s."
                    .formatted(messagePostfix));
        }
        softAssert.assertEquals(responseDto.getScreenName(), screenName, "screenName is not correct %s."
                .formatted(messagePostfix));
        softAssert.assertEquals(responseDto.getGender(), gender, "Gender is inot correct %s."
                .formatted(messagePostfix));
        softAssert.assertEquals(responseDto.getAge(), age, "Age is not correct %s."
                .formatted(messagePostfix));
        softAssert.assertEquals(responseDto.getRole(), userRole, "User role is not correct %s."
                .formatted(messagePostfix));
    }

    protected static ExtendedPlayerDto createPlayer(String editor, PlayerController playerController, Integer age, Gender gender, String login, String password, UserRole userRole, String screenName) {
        return playerController.createPlayer(
                        editor,
                        String.valueOf(age),
                        gender.toString(),
                        login,
                        password,
                        userRole.toString(),
                        screenName)
                .as(ExtendedPlayerDto.class);
    }
}
