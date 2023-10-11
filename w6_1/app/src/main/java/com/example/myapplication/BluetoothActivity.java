package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BluetoothActivity extends AppCompatActivity {

    private final static int BLUETOOTH_REQUEST_CODE = 1;

    private BluetoothAdapter bluetooth;

    private Button btnScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BluetoothActivity.this, new String[] {android.Manifest.permission.BLUETOOTH_SCAN}, BLUETOOTH_REQUEST_CODE);
                return;
            }
            bluetooth.startDiscovery();
        }

        btnScan = findViewById(R.id.scan);
        btnScan.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_SCAN}, 1);
                return;
            }

            if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }

            if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }

            bluetooth = BluetoothAdapter.getDefaultAdapter();

            int scanMode = bluetooth.getScanMode();

            switch (scanMode) {
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                    Toast.makeText(BluetoothActivity.this, "Chế độ: Cho phép tìm thấy khi tìm kiếm", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                    Toast.makeText(BluetoothActivity.this, "Chế độ: Cho phép tìm thấy khi đã từng kết nối", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.SCAN_MODE_NONE:
                    Toast.makeText(BluetoothActivity.this, "Chế độ: Không cho phép tìm kiếm", Toast.LENGTH_SHORT).show();
                    break;
            }

            if (bluetooth.isDiscovering()) {
                bluetooth.cancelDiscovery();
            }

            // Cho phép tìm thấy khi tìm kiếm

            if (scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this,
                            new String[]{android.Manifest.permission.BLUETOOTH_CONNECT},
                            1);
                } else {
                    int requestCode = 1;
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivityForResult(discoverableIntent, requestCode);
                }
            } else {
                // Nếu đã cho phép tìm thấy khi tìm kiếm, tiến hành tìm kiếm
                bluetooth.startDiscovery();

                BluetoothReceiver receiver = new BluetoothReceiver();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver, filter);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(new BluetoothReceiver());
    }
}