package admin.com.almoskyadmin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import admin.com.almoskyadmin.AlmoskyAdmin;


public class Utility {




    //checking network state
    public static boolean isNetworkOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public static void clearTempData(){

        AlmoskyAdmin.getInst().setSelectedOrder(null);
        AlmoskyAdmin.getInst().setCurentOrderId("");
        AlmoskyAdmin.getInst().setDeliveryType(0);
        AlmoskyAdmin.getInst().setDrycleanList(null);
        AlmoskyAdmin.getInst().setWashList(null);

        AlmoskyAdmin.getInst().setIronList(null);
        AlmoskyAdmin.getInst().setWashList1(null);
        AlmoskyAdmin.getInst().setDrycleanList1(null);
        AlmoskyAdmin.getInst().setIronList1(null);
        AlmoskyAdmin.getInst().setSelectedOrderType("");
        AlmoskyAdmin.getInst().setSelecterOrderId(0);
        AlmoskyAdmin.getInst().setDrycleanList1temp(null);
        AlmoskyAdmin.getInst().setWashList1temp(null);
      //  AlmoskyAdmin.getInst().setDrycleanList1temp(null);
        AlmoskyAdmin.getInst().setIronList1temp(null);

    }




}
