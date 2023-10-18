package com.wear.myapp;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.wear.myapp.databinding.ActivityMainBinding;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.msgBtn.setOnClickListener(view -> {
            String message = binding.msgEdt.getText().toString();
            if (!message.isEmpty()) {
                sendData(message);
                binding.msgEdt.setText("");
            }


        });

    }

    private void sendData(String message) {
        DataClient dataClient = Wearable.getDataClient(this);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/data-path");
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("message", message);

        dataClient.putDataItem(putDataMapRequest.asPutDataRequest()).addOnSuccessListener(dataItem -> {
            Log.d(TAG, "Data sent successfully");
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to send data", e);
        });

    }


}