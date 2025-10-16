package org.andrewfroze.tests.player;

import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.tests.BaseTest;
import org.andrewfroze.utils.RandomUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.andrewfroze.framework.enums.Gender.FEMALE;
import static org.andrewfroze.framework.enums.Gender.MALE;
import static org.andrewfroze.framework.enums.UserRole.*;
import static org.andrewfroze.utils.TestDataHelper.*;

public class CreatePlayerTest extends BaseTest {

    @DataProvider(name = "GenderRoleEditor")
    private Object[][] getGenderRoleEditor() {
        return new Object[][] {
                {MALE.toString(), ADMIN.toString(), supervisorEditor},
                {FEMALE.toString(), ADMIN.toString(), supervisorEditor},
                {MALE.toString(), USER.toString(), supervisorEditor},
                {FEMALE.toString(), USER.toString(), supervisorEditor},
                {MALE.toString(), ADMIN.toString(), adminEditor},
                {FEMALE.toString(), ADMIN.toString(), adminEditor},
                {MALE.toString(), USER.toString(), adminEditor},
                {FEMALE.toString(), USER.toString(), adminEditor},
        };
    }

    @Test(dataProvider = "GenderRoleEditor")
    public void validatePlayerCreation(String gender, String userRole, String editor) {
        var playerController = new PlayerController();

        Integer age = getAllowedAge();
        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();

        var response = playerController.createPlayer(
                editor,
                String.valueOf(age),
                gender,
                login,
                password,
                userRole,
                screenName
        );

        var softAssert = new SoftAssert();
        validateStatusCode(softAssert, response, 201, "in create user request");
        validateUser(softAssert, response, gender, userRole, login, password, screenName, age, true);
        softAssert.assertAll();
    }

    @Test(dataProvider = "validAges")
    public void validateCreateUsersWithValidAges(int age) {
        var playerController = new PlayerController();

        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var gender = getRandomGender().toString();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                login,
                password,
                userRole,
                screenName
        );
        var softAssert = new SoftAssert();
        validateStatusCode(softAssert, response, 201, "in create user request");
        validateUser(softAssert, response, gender, userRole, login, password, screenName, age, true);
        softAssert.assertAll();
    }

    @Test(dataProvider = "invalidAges")
    public void validatePlayerCreationWithInvalidAge(int age) {
        var playerController = new PlayerController();

        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var gender = getRandomGender().toString();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                login,
                password,
                userRole,
                screenName
        );

        validateStatusCode(response, 400);
    }

    @Test
    public void validateCreationOfSupervisor() {
        var playerController = new PlayerController();

        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var gender = getRandomGender().toString();
        var age = getAllowedAge();
        var userRole = SUPERVISOR.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                login,
                password,
                userRole,
                screenName
        );

        validateStatusCode(response, 400);
    }

    @Test(dataProvider = "missingFieldData")
    public void validateRequiredFields(String missingField, String age, String gender, String login, String password, String userRole, String screenName) {
        var playerController = new PlayerController();

        var response = playerController.createPlayer(
                supervisorEditor,
                age,
                gender,
                login,
                password,
                userRole,
                screenName
        );

        validateStatusCode(response, 400, "for missing field " + missingField);
    }

    @DataProvider(name = "missingFieldData")
    private Object[][] getMissingFieldData() {
        var age = String.valueOf(getAllowedAge());
        var gender = getRandomGender().toString();
        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var userRole = USER.toString();

        return new Object[][] {
                {"age", null, gender, login + "1", password, userRole, screenName + "1"},
                {"gender", age, null, login + "2", password, userRole, screenName + "2"},
                {"login", age, gender, null, password, userRole, screenName + "3"},
                {"password", age, gender, login + "4", null, userRole, screenName + "4"},
                {"role", age, gender, login + "5", password, null, screenName + "5"},
                {"screenName", age, gender, login + "6", password, userRole, null},
                {"age", "", gender, login + "7", password, userRole, screenName + "7"},
                {"gender", age, "", login + "8", password, userRole, screenName + "8"},
                {"login", age, gender, "", password, userRole, screenName + "9"},
                {"password", age, gender, login + "10", "", userRole, screenName + "10"},
                {"role", age, gender, login + "11", password, "", screenName + "11"},
                {"screenName", age, gender, login + "12", password, userRole, ""}
        };
    }

    @Test
    public void validateUniqueLogin() {
        var playerController = new PlayerController();

        var existingPlayer = RandomUtil.getRandomValueFromList(
                        playerController.getAllPlayersDto().getPlayers());

        var existingPlayerLogin = playerController.getPlayerByIdDto(existingPlayer.getId()).getLogin();

        var newScreenName = getNewScreenName();
        var password = getValidPassword();
        var age = getAllowedAge();
        var gender = getRandomGender().toString();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                existingPlayerLogin,
                password,
                userRole,
                newScreenName
        );

        validateStatusCode(response, 400);
    }

    @Test
    public void validateUniqueScreenName() {
        var playerController = new PlayerController();

        var existingPlayer = RandomUtil.getRandomValueFromList(
                playerController.getAllPlayersDto().getPlayers());

        var password = getValidPassword();
        var age = getAllowedAge();
        var gender = getRandomGender().toString();
        var login = getNewLogin();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                login,
                password,
                userRole,
                existingPlayer.getScreenName()
        );

        validateStatusCode(response, 400);
    }

    @Test(dataProvider = "invalidPasswords")
    public void validateInvalidPasswords(String invalidPassword, String reason) {
        var playerController = new PlayerController();

        var login = getNewLogin();
        var screenName = getNewScreenName();
        var age = getAllowedAge();
        var gender = getRandomGender().toString();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                gender,
                login,
                invalidPassword,
                userRole,
                screenName
        );

        validateStatusCode(response, 400, "for '%s' reason".formatted(reason));
    }

    @DataProvider(name = "invalidPasswords")
    private Object[][] getInvalidPasswords() {
        return new Object[][]{
                {"abcdefg", "no digits"},
                {"1234567", "no letters"},
                {"abc12", "too short"},
                {"abc123456789012345", "too long"},
                {"дabcdefg123", "non-latin letters"}
        };
    }

    @Test
    public void validateInvalidGender() {
        var playerController = new PlayerController();

        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var age = getAllowedAge();
        var userRole = USER.toString();

        var response = playerController.createPlayer(
                supervisorEditor,
                String.valueOf(age),
                "transgender",
                login,
                password,
                userRole,
                screenName
        );

        validateStatusCode(response, 400);
    }
}
