package org.andrewfroze.framework.models.response;

public class ExtendedPlayerDto extends PlayerDto {

    private String login;
    private String password;
    private String role;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
