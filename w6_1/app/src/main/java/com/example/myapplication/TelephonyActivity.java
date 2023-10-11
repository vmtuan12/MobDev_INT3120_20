package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;

public class TelephonyActivity extends AppCompatActivity implements TelephonyCallback.CallStateListener {

    private final String serviceName = Context.TELEPHONY_SERVICE;
    private TelephonyManager telephonyManager;
    private Button intentCallButton;

    private Button mainButton;

    private Button intentSmsButton;
    private Button smsMngButton;

    private TextView textView;

    SmsManager smsManager;

    private PhoneStateChangedReceiver phoneStateChangedReceiver;

    private MySMSReceiver mySMSReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony);

        firstDeclaration();
        buttonBinding();

        registerReceiver(phoneStateChangedReceiver, new IntentFilter("android.intent.action.PHONE_STATE"));
        registerReceiver(mySMSReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        String info = telephonyManager.getSimOperatorName() + " " + telephonyManager.getPhoneType();
        textView.setText(info);
    }

    private void firstDeclaration() {
        intentCallButton = findViewById(R.id.btn_intent_call);
        telephonyManager = (TelephonyManager) getSystemService(serviceName);
        phoneStateChangedReceiver = new PhoneStateChangedReceiver();
        mySMSReceiver = new MySMSReceiver();
        textView = findViewById(R.id.info);
        mainButton = findViewById(R.id.button_main);
        intentSmsButton = findViewById(R.id.button_intent_sms);
        smsManager = this.getSystemService(SmsManager.class);
        smsMngButton = findViewById(R.id.button_sms_mng);
    }

    private void buttonBinding() {
        intentCallButton.setOnClickListener(v -> {
            Intent newCall = new Intent(Intent.ACTION_DIAL);
            newCall.setData(Uri.parse("tel:555-1111"));
            startActivity(newCall);
        });

        mainButton.setOnClickListener(v -> {
            startActivity(new Intent(TelephonyActivity.this, MainActivity.class));
        });

        intentSmsButton.setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:55512345"));
            smsIntent.putExtra("body", "Press send to send me");
            startActivity(smsIntent);
        });

        smsMngButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(TelephonyActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TelephonyActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return;
            }

            smsManager.sendTextMessage("55511123", null, "message", null, null);
        });
    }

    @Override
    public void onCallStateChanged(int state) {

    }
}