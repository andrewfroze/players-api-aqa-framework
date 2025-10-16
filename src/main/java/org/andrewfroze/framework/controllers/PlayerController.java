package org.andrewfroze.framework.controllers;

import io.restassured.response.Response;
import org.andrewfroze.framework.clients.BaseClient;
import org.andrewfroze.framework.models.request.GetPlayerByIdBody;
import org.andrewfroze.framework.models.request.UpdatePlayerBody;
import org.andrewfroze.framework.models.response.AllPlayersDto;
import org.andrewfroze.framework.models.response.ExtendedPlayerDto;

import java.util.HashMap;
import java.util.Map;

public class PlayerController extends BaseClient {

    public Response createPlayer(String editor,
                                 String age,
                                 String gender,
                                 String login,
                                 String password,
                                 String role,
                                 String screenName) {
        var queryParams = formQueryParameters(age, gender, login, password, role, screenName);
        return getBaseSpecification()
                .queryParams(queryParams)
                .pathParam("editor", editor)
                .get("/player/create/{editor}");
    }

    public Response getPlayerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id should not be null.");
        }

        return getBaseSpecification()
                .body(new GetPlayerByIdBody(id))
                .post("/player/get");
    }

    public ExtendedPlayerDto getPlayerByIdDto(Long id) {
        return getPlayerById(id).as(ExtendedPlayerDto.class);
    }

    public Response getAllPlayers() {
        return getBaseSpecification()
                .get("/player/get/all");
    }

    public AllPlayersDto getAllPlayersDto() {
        return getAllPlayers().as(AllPlayersDto.class);
    }

    public Response updatePlayer(String editor, Long id, UpdatePlayerBody body) {
        return getBaseSpecification()
                .pathParam("editor", editor)
                .pathParam("id", id)
                .body(body)
                .patch("/player/update/{editor}/{id}");
    }

    public Response deletePlayer(String editor, Long id) {
        return getBaseSpecification()
                .pathParam("editor", editor)
                .body(new GetPlayerByIdBody(id))
                .delete("/player/delete/{editor}");
    }

    private static Map<String, String> formQueryParameters(String age, String gender, String login, String password, String role, String screenName) {
        Map<String, String> queryParams = new HashMap<>();
        if (age != null) {
            queryParams.put("age", age);
        }
        if (gender != null) {
            queryParams.put("gender", gender);
        }
        if (login != null) {
            queryParams.put("login", login);
        }
        if (password != null) {
            queryParams.put("password", password);
        }
        if (role != null) {
            queryParams.put("role", role);
        }
        if (screenName != null) {
            queryParams.put("screenName", screenName);
        }
        return queryParams;
    }
}
