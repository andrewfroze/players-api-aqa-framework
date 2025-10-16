package org.andrewfroze.framework.models.response;

public class PlayerDto {

    protected Long id;
    protected String screenName;
    protected String gender;
    protected Integer age;

    public Long getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }
}
