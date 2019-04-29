package admin.com.almoskyadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CustomerListdto {

    @SerializedName("Result1")
    @Expose
    private List<Customer> result = null;

    public List<Customer> getResult() {
        return result;
    }

    public void setResult(List<Customer> result) {
        this.result = result;
    }

}
