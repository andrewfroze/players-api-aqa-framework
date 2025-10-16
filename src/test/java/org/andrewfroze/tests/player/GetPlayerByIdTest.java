package org.andrewfroze.tests.player;

import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.framework.models.response.ExtendedPlayerDto;
import org.andrewfroze.tests.BaseTest;
import org.andrewfroze.utils.RandomUtil;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetPlayerByIdTest extends BaseTest {

    @Test
    public void validateReceivingPlayerById() {
        var playerController = new PlayerController();
        var testPlayer = RandomUtil.getRandomValueFromList(
                playerController.getAllPlayersDto().getPlayers());

        var response = playerController.getPlayerById(testPlayer.getId());

        validateStatusCode(response, 200);

        var responseDto = response.as(ExtendedPlayerDto.class);
        var softAssert = new SoftAssert();
        softAssert.assertEquals(responseDto.getId(), testPlayer.getId(), "Id is not correct.");
        softAssert.assertEquals(responseDto.getScreenName(), testPlayer.getScreenName(),
                "screenName is not correct.");
        softAssert.assertEquals(responseDto.getGender(), testPlayer.getGender(), "Gender is not correct.");
        softAssert.assertEquals(responseDto.getAge(), testPlayer.getAge(), "Age is not correct.");
        softAssert.assertAll();
    }

    @Test
    public void validateGetPlayerByInvalidId() {
        var playerController = new PlayerController();
        long invalidId = -1;

        var response = playerController.getPlayerById(invalidId);

        validateStatusCode(response, 404);
    }

    @Test
    public void validateGetPlayerByMaxLongId() {
        var playerController = new PlayerController();
        long maxLongId = Long.MAX_VALUE;

        var response = playerController.getPlayerById(maxLongId);

        validateStatusCode(response, 404);
    }
}
