package com.amri.careercenter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnBlutooth, btnBack;
    private BluetoothAdapter BA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     //   setTheme(R.style.AppTheme_SplashScreen);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        btnBlutooth =(Button)findViewById(R.id.btnBluetooth);
        btnBack =(Button)findViewById(R.id.btnBack);
        BA = (BluetoothAdapter.getDefaultAdapter());
    }

    public void on(View v){
        if(!BA.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn,0);
            Toast.makeText(getApplicationContext(),"Menghidupkan Bluetooth",
                Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Bluetooth Telah Hidup",
            Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View v){
        moveTaskToBack(true);
    }
}
