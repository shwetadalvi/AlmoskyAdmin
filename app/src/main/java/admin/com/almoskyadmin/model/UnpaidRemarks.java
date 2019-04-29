package admin.com.almoskyadmin.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnpaidRemarks {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("remarksId")
    @Expose
    private Integer remarksId;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRemarksId() {
        return remarksId;
    }

    public void setRemarksId(Integer remarksId) {
        this.remarksId = remarksId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}