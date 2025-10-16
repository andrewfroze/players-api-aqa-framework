package org.andrewfroze.framework.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.andrewfroze.framework.config.Config.basePath;
import static org.andrewfroze.framework.config.Config.baseUrl;

public abstract class BaseClient {

    protected RequestSpecification getBaseSpecification() {
        var filters = new ArrayList<>(List.of(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()));
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseUrl())
                .basePath(basePath())
                .filters(filters);
    }
}
