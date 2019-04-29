package admin.com.almoskyadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Customer {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("UserName")
    @Expose
    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

