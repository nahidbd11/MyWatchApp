package com.wear.myapp;

import androidx.annotation.NonNull;


import android.app.Activity;

import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.Objects;

public class MainActivity extends Activity implements DataClient.OnDataChangedListener {

    private DataClient dataClient;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate triggered");

        // Set up data client to listen for data changes
        Wearable.getDataClient(this).addListener(this);


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener to avoid memory leaks

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.d(TAG, "onDataChanged triggered");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                if (Objects.equals(path, "/data-path")) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(dataEvent.getDataItem());
                    String message = dataMapItem.getDataMap().getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    // Find the TextView by its id
                    TextView textView = findViewById(R.id.txtV);
                    textView.setText(message);

                    // Now you can interact with the TextView

                    System.out.println(message + ">>>>>>>>>>>>>>>>>>>>>>>>");

                }
            }
        }
    }
}