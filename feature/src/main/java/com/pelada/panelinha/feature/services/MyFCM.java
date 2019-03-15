package com.pelada.panelinha.feature.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFCM extends FirebaseMessagingService {

    private static final String TAG = "MyFCM";

    @Override
    public void onNewToken(String token){
        Log.i(TAG, "Refreshed token: " + token);

//        sendRegistrationToServer(token);
    }
}
