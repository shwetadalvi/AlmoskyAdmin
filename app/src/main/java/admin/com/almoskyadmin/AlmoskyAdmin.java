package admin.com.almoskyadmin;




import java.util.ArrayList;

import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.model.data;
import admin.com.almoskyadmin.model.data1;
import admin.com.almoskyadmin.model.priceListdto;

public class AlmoskyAdmin {

    public static AlmoskyAdmin inst;


    public ArrayList<data.Result> drycleanList;
    public ArrayList<data.Result> ironList;
    public ArrayList<data.Result> washList;

    public ArrayList<data1.Detail.Item> drycleanList1;
    public ArrayList<data1.Detail.Item> ironList1;
    public ArrayList<data1.Detail.Item> washList1;

    public ArrayList<data1.Detail.Item> drycleanList1temp;
    public ArrayList<data1.Detail.Item> ironList1temp;
    public ArrayList<data1.Detail.Item> washList1temp;

    public ArrayList<priceListdto.Detail> itempriceList;
    public int deliveryType;
    public int cartcount;
    public int cartamount;
    public boolean offer;
    public double vatRate,nasabRate;

    public String curentOrderId;
    public ArrayList<OrderListdto.Result> currentOrders;
    public OrderListdto.Result selectedOrder;
    public String selectedOrderType;
    public int selectedOrderServiceId;

    boolean update;

    public int selecterOrderId;


    private AlmoskyAdmin(){

    }


    public static AlmoskyAdmin getInst(){
        if(inst==null){
            inst=new AlmoskyAdmin();
        }
        return  inst;
    }


    public ArrayList<data.Result> getDrycleanList() {
        return drycleanList;
    }

    public void setDrycleanList(ArrayList<data.Result> drycleanList) {
        this.drycleanList = drycleanList;
    }


    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getSelectedOrderType() {
        return selectedOrderType;
    }

    public void setSelectedOrderType(String selectedOrderType) {
        this.selectedOrderType = selectedOrderType;
    }

    public ArrayList<data1.Detail.Item> getDrycleanList1() {
        return drycleanList1;
    }

    public void setDrycleanList1(ArrayList<data1.Detail.Item> drycleanList1) {
        this.drycleanList1 = drycleanList1;
    }


    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public ArrayList<data1.Detail.Item> getIronList1() {
        return ironList1;
    }

    public void setIronList1(ArrayList<data1.Detail.Item> ironList1) {
        this.ironList1 = ironList1;
    }

    public ArrayList<data1.Detail.Item> getWashList1() {
        return washList1;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public double getNasabRate() {
        return nasabRate;
    }

    public void setNasabRate(double nasabRate) {
        this.nasabRate = nasabRate;
    }

    public ArrayList<priceListdto.Detail> getItempriceList() {
        return itempriceList;
    }

    public void setItempriceList(ArrayList<priceListdto.Detail> itempriceList) {
        this.itempriceList = itempriceList;
    }

    public int getCartcount() {
        return cartcount;
    }

    public void setCartcount(int cartcount) {
        this.cartcount = cartcount;
    }

    public int getCartamount() {
        return cartamount;
    }

    public void setCartamount(int cartamount) {
        this.cartamount = cartamount;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public void setWashList1(ArrayList<data1.Detail.Item> washList1) {
        this.washList1 = washList1;
    }

    public int getSelecterOrderId() {
        return selecterOrderId;
    }

    public void setSelecterOrderId(int selecterOrderId) {
        this.selecterOrderId = selecterOrderId;
    }

    public ArrayList<data.Result> getIronList() {
        return ironList;
    }

    public void setIronList(ArrayList<data.Result> ironList) {
        this.ironList = ironList;
    }

    public ArrayList<data.Result> getWashList() {
        return washList;
    }

    public void setWashList(ArrayList<data.Result> washList) {
        this.washList = washList;
    }

    public String getCurentOrderId() {
        return curentOrderId;
    }

    public void setCurentOrderId(String curentOrderId) {
        this.curentOrderId = curentOrderId;
    }

    public ArrayList<OrderListdto.Result> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(ArrayList<OrderListdto.Result> currentOrders) {
        this.currentOrders = currentOrders;
    }

    public OrderListdto.Result getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderListdto.Result selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public ArrayList<data1.Detail.Item> getDrycleanList1temp() {
        return drycleanList1temp;
    }

    public void setDrycleanList1temp(ArrayList<data1.Detail.Item> drycleanList1temp) {
        this.drycleanList1temp = drycleanList1temp;
    }

    public ArrayList<data1.Detail.Item> getIronList1temp() {
        return ironList1temp;
    }

    public void setIronList1temp(ArrayList<data1.Detail.Item> ironList1temp) {
        this.ironList1temp = ironList1temp;
    }

    public ArrayList<data1.Detail.Item> getWashList1temp() {
        return washList1temp;
    }

    public void setWashList1temp(ArrayList<data1.Detail.Item> washList1temp) {
        this.washList1temp = washList1temp;
    }

    public int getSelectedOrderServiceId() {
        return selectedOrderServiceId;
    }

    public void setSelectedOrderServiceId(int selectedOrderServiceId) {
        this.selectedOrderServiceId = selectedOrderServiceId;
    }
}
