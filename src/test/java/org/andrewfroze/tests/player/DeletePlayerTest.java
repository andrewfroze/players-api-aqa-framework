package org.andrewfroze.tests.player;

import org.andrewfroze.framework.controllers.PlayerController;
import org.andrewfroze.tests.BaseTest;
import org.testng.annotations.Test;

import static org.andrewfroze.framework.enums.UserRole.USER;
import static org.andrewfroze.utils.TestDataHelper.*;

public class DeletePlayerTest extends BaseTest {

    @Test
    public void validateSuccessfulPlayerDeletion() {
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

        var response = playerController.deletePlayer(
                supervisorEditor,
                createdPlayer.getId()
        );

        validateStatusCode(response, 204, "successful deletion");

        var getResponse = playerController.getPlayerById(createdPlayer.getId());
        validateStatusCode(getResponse, 404, "player should be deleted");
    }

    @Test
    public void validateDeletionOfNonexistentPlayer() {
        var playerController = new PlayerController();

        long nonExistentId = Long.MAX_VALUE;

        var response = playerController.deletePlayer(
                supervisorEditor,
                nonExistentId
        );

        validateStatusCode(response, 404, "deletion of nonexistent player should fail");
    }

    // test on deletion supervisor user could be added but by requirement 'Tests should be written as if you're working with a production system, where every change is costly and risky.' I didn't create it, just to not delete single supervisor :)
}
