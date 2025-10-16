package org.andrewfroze.tests.player;

import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.tests.BaseTest;
import org.testng.annotations.Test;

public class GetAllPlayersTest extends BaseTest {

    @Test
    public void validateReceivingOfAllPlayers() {
        var playerController = new PlayerController();
        var response = playerController.getAllPlayers();

        validateStatusCode(response, 200);
    }
}
