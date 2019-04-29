package admin.com.almoskyadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UnpaidRemarkList {
    @SerializedName("Result")
    @Expose
    private List<UnpaidRemarks> result = null;

    public List<UnpaidRemarks> getResult() {
        return result;
    }

    public void setResult(List<UnpaidRemarks> result) {
        this.result = result;
    }

}