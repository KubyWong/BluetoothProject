package com.example.kuby.myapplication;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuby.myapplication.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private ListView lv;
    private Button btn;
    private BluetoothManager bluetoothManager;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter.LeScanCallback leScanCallback;

    private boolean isScan = false;
    private Handler handler = new Handler();
    private static final int SCAN_TIME = 10 * 1000;
    private List<Map<String, String>> maps;
    private List<BluetoothDevice> devices;
    private SimpleAdapter adapter;
//    private List<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用

        if (!bluetoothAdapter.isEnabled()) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initData() {
        maps = new ArrayList<Map<String, String>>();
        devices = new ArrayList<>();
        
        //1.获取BluetoothManager
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        //2.获取BluetoothAdapter
        bluetoothAdapter = bluetoothManager.getAdapter();

    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv_show);
        lv = (ListView) findViewById(R.id.lv_devices);
        btn = (Button) findViewById(R.id.btn_search);
        btn.setOnClickListener(this);

        adapter = new SimpleAdapter(this, maps,R.layout.item_device,new String[]{"title"},new int[]{R.id.tv_mac});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:

                if(isScan){
                    Toast.makeText(MainActivity.this, "已经在搜索中,请稍后!", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv.setText("开始搜索");
                isScan = true;

                //3.创建BluetoothAdapter.LeScanCallback
                leScanCallback = new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.i(" " + device.getAddress());
                                if(!devices.contains(device)) {
                                    String dName = device.getName();
                                    String dAddress = device.getAddress();
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("title", dAddress + "(" + dName + ")");
                                    maps.add(map);
                                    devices.add(device);
                                    adapter.notifyDataSetChanged();

                                    LogUtils.i("name---> " + device.getName());
                                    LogUtils.i("address---> " + device.getAddress());
                                }
                            }
                        });
                    }
                };
                bluetoothAdapter.startLeScan(leScanCallback);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("搜索结束");
                        isScan = false;
                        bluetoothAdapter.stopLeScan(leScanCallback);
                    }
                }, SCAN_TIME);

                break;
            default:
                break;
        }
    }
}
