package org.andrewfroze.framework.models.request;

public class UpdatePlayerBody {

    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getScreenName() {
        return screenName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final UpdatePlayerBody instance = new UpdatePlayerBody();

        public Builder age(Integer age) {
            instance.age = age;
            return this;
        }

        public Builder gender(String gender) {
            instance.gender = gender;
            return this;
        }

        public Builder login(String login) {
            instance.login = login;
            return this;
        }

        public Builder password(String password) {
            instance.password = password;
            return this;
        }

        public Builder role(String role) {
            instance.role = role;
            return this;
        }

        public Builder screenName(String screenName) {
            instance.screenName = screenName;
            return this;
        }

        public UpdatePlayerBody build() {
            return instance;
        }
    }
}
