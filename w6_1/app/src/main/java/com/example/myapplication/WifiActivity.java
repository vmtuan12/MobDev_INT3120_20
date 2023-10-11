package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {
    private TextView networkInfoTextView, wifiInfoTextView, status;
    private WifiManager mWifiManager;
    public static ListView listView;
    private ConnectivityManager connectivity;

    ConnectivityChangedReceiver receiver = new ConnectivityChangedReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        listView = findViewById(R.id.wifiList);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfoTextView = findViewById(R.id.wifiInfo);
        Button scanButton = findViewById(R.id.scan_wifi);
        Button wifiConfigButton = findViewById(R.id.wifi_config);
        Button mainButton = findViewById(R.id.btn_main);
        connectivity = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfoTextView = findViewById(R.id.wifiStatus);

        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            networkInfoTextView.setText("Network Info: " + activeNetwork);
        } else {
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
        }
        wifiInfoTextView.setText("Wifi Info: " + mWifiManager.getConnectionInfo().toString());

        registerReceiver(receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        mWifiManager.startScan();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });

        wifiConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(WifiActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(WifiActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                Log.e("configuration", mWifiManager.getConfiguredNetworks().toString());
            }
        });

        mainButton.setOnClickListener(v -> {
            startActivity(new Intent(WifiActivity.this, MainActivity.class));
        });
    }

    private void scanWifi() {
        if (ActivityCompat.checkSelfPermission(WifiActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WifiActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            mWifiManager.startScan();
            List<ScanResult> scanResults = mWifiManager.getScanResults();
            List<String> wifiList = new ArrayList<>();
            for (ScanResult scanResult : scanResults) {
                wifiList.add(scanResult.toString());
            }
            listView.setAdapter(new ArrayAdapter<>(WifiActivity.this, android.R.layout.simple_list_item_1, wifiList));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}