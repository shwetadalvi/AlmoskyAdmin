package admin.com.almoskyadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import admin.com.almoskyadmin.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class notifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        System.out.println("inside notif");

        new SweetAlertDialog(this)
                .setTitleText("New Order")
                .setContentText("New Order Arrived")
                .show();
    }
}
