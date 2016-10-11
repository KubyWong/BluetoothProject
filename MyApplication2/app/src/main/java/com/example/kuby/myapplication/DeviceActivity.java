package com.example.kuby.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeviceActivity extends AppCompatActivity {

    private TextView tv_device_name,tv_device_address,tv_device_status;
    private Button btn_connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        tv_device_name = (TextView) findViewById(R.id.tv_device_name);
        tv_device_address = (TextView) findViewById(R.id.tv_device_address);
        tv_device_status = (TextView) findViewById(R.id.tv_device_status);

        btn_connect = (Button) findViewById(R.id.btn_connect);
    }

}
