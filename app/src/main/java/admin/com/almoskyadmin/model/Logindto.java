package admin.com.almoskyadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Logindto {

    @SerializedName("Result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public class Result {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("Admin")
        @Expose
        private Integer admin;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAdmin() {
            return admin;
        }

        public void setAdmin(Integer admin) {
            this.admin = admin;
        }

    }
/*
    @SerializedName("Result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Profile")
        @Expose
        private List<Profile> profile = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Profile> getProfile() {
            return profile;
        }

        public void setProfile(List<Profile> profile) {
            this.profile = profile;
        }

        public class Profile {

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("Admin")
            @Expose
            private Integer admin;

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Integer getAdmin() {
                return admin;
            }

            public void setAdmin(Integer admin) {
                this.admin = admin;
            }

        }


    }
*/


}


