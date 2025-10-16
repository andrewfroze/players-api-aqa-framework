package org.andrewfroze.tests.player;

import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.framework.enums.Gender;
import org.andrewfroze.framework.models.request.UpdatePlayerBody;
import org.andrewfroze.tests.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.andrewfroze.framework.enums.UserRole.ADMIN;
import static org.andrewfroze.framework.enums.UserRole.USER;
import static org.andrewfroze.utils.RandomUtil.*;
import static org.andrewfroze.utils.TestDataHelper.*;
import static org.andrewfroze.utils.TestDataHelper.getNewScreenName;

public class UpdatePlayerTest extends BaseTest {

    @DataProvider(name = "Editor")
    private Object[][] getGenderRoleEditor() {
        return new Object[][] {
                {supervisorEditor},
                {adminEditor}
        };
    }

    @Test(dataProvider = "Editor")
    public void validateUpdatingPlayer(String editor) {
        var playerController = new PlayerController();

        Integer age = getAllowedAge();
        var login = getNewLogin();
        var password = getValidPassword();
        var screenName = getNewScreenName();
        var gender = getRandomGender();
        var userRole = getUserRoleToCreateUser();

        var createdPlayer = createPlayer(editor, playerController, age, gender, login, password, userRole, screenName);

        Integer updatedAge = getAllowedAge();
        var updatedLogin = getNewLogin();
        var updatedPassword = getValidPassword();
        var updatedScreenName = getNewScreenName();
        var updatedGender = getRandomValueFromArrayExceptValue(Gender.values(), gender);
        var updatedUserRole = getRandomValueFromListExceptValue(List.of(ADMIN, USER), userRole);

        var response = playerController.updatePlayer(
                editor,
                createdPlayer.getId(),
                UpdatePlayerBody
                        .builder()
                        .age(updatedAge)
                        .gender(updatedGender.toString())
                        .login(updatedLogin)
                        .password(updatedPassword)
                        .role(updatedUserRole.toString())
                        .screenName(updatedScreenName)
                        .build());

        var softAssert = new SoftAssert();
        validateStatusCode(softAssert, response, 200, "in update user request");
        validateUser(softAssert, response,
                updatedGender.toString(),
                updatedUserRole.toString(),
                updatedLogin,
                updatedPassword,
                updatedScreenName,
                updatedAge,
                false);
        softAssert.assertAll();
    }

    @Test
    public void validateUpdateUniqueLogin() {
        var playerController = new PlayerController();

        var existingPlayer = getRandomValueFromList(playerController.getAllPlayersDto().getPlayers());
        var loginToUse = playerController.getPlayerByIdDto(existingPlayer.getId()).getLogin();

        var createdPlayer = createPlayer(supervisorEditor, playerController,
                getAllowedAge(), getRandomGender(), getNewLogin(), getValidPassword(), getUserRoleToCreateUser(), getNewScreenName());

        var response = playerController.updatePlayer(
                supervisorEditor,
                createdPlayer.getId(),
                UpdatePlayerBody.builder()
                        .login(loginToUse)
                        .build());

        validateStatusCode(response, 409, "for duplicate login");
    }

    @Test
    public void validateUpdateUniqueScreenName() {
        var playerController = new PlayerController();

        var existingPlayer = getRandomValueFromList(playerController.getAllPlayersDto().getPlayers());
        var screenNameToUse = existingPlayer.getScreenName();

        var createdPlayer = createPlayer(supervisorEditor, playerController,
                getAllowedAge(), getRandomGender(), getNewLogin(), getValidPassword(), getUserRoleToCreateUser(), getNewScreenName());

        var response = playerController.updatePlayer(
                supervisorEditor,
                createdPlayer.getId(),
                UpdatePlayerBody.builder()
                        .screenName(screenNameToUse)
                        .build());

        validateStatusCode(response, 409, "for duplicate screenName");
    }

    @Test
    public void validateInvalidGenderUpdate() {
        var playerController = new PlayerController();

        var createdPlayer = createPlayer(supervisorEditor, playerController,
                getAllowedAge(), getRandomGender(), getNewLogin(), getValidPassword(), getUserRoleToCreateUser(), getNewScreenName());

        var response = playerController.updatePlayer(
                supervisorEditor,
                createdPlayer.getId(),
                UpdatePlayerBody.builder()
                        .gender("alien")
                        .build());

        validateStatusCode(response, 400, "for invalid gender");
    }

    @Test(dataProvider = "invalidAges")
    public void validatePlayerUpdateWithInvalidAge(int age) {
        var playerController = new PlayerController();

        var createdPlayer = createPlayer(
                supervisorEditor,
                playerController,
                getAllowedAge(),
                getRandomGender(),
                getNewLogin(),
                getValidPassword(),
                USER,
                getNewScreenName()
        );

        var response = playerController.updatePlayer(
                supervisorEditor,
                createdPlayer.getId(),
                UpdatePlayerBody.builder()
                        .age(age)
                        .build()
        );

        validateStatusCode(response, 400, "for invalid age: " + age);
    }
}
