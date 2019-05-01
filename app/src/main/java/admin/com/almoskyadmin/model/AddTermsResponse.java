package admin.com.almoskyadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddTermsResponse {
    @SerializedName("Result")
    @Expose
    private String Result;


    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}