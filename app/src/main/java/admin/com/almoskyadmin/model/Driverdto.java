package admin.com.almoskyadmin.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Driverdto {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result = null;

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

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Full_Name")
        @Expose
        private String fullName;
        @SerializedName("Last_Name")
        @Expose
        private Object lastName;
        @SerializedName("Mobile")
        @Expose
        private Object mobile;
        @SerializedName("Username")
        @Expose
        private Object username;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("Admin_Status")
        @Expose
        private Integer adminStatus;
        @SerializedName("User_ID_1")
        @Expose
        private Object userID1;
        @SerializedName("User_Deleted")
        @Expose
        private Integer userDeleted;
        @SerializedName("RegistrationToken")
        @Expose
        private Object registrationToken;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Object getLastName() {
            return lastName;
        }

        public void setLastName(Object lastName) {
            this.lastName = lastName;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getAdminStatus() {
            return adminStatus;
        }

        public void setAdminStatus(Integer adminStatus) {
            this.adminStatus = adminStatus;
        }

        public Object getUserID1() {
            return userID1;
        }

        public void setUserID1(Object userID1) {
            this.userID1 = userID1;
        }

        public Integer getUserDeleted() {
            return userDeleted;
        }

        public void setUserDeleted(Integer userDeleted) {
            this.userDeleted = userDeleted;
        }

        public Object getRegistrationToken() {
            return registrationToken;
        }

        public void setRegistrationToken(Object registrationToken) {
            this.registrationToken = registrationToken;
        }

    }
}
