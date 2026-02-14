package com.example.aidlrandomservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Random;

public class MyRandomService extends Service {
    private final Random random = new Random();

    private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public int getRandomNumber() {
            // Generate a random number between 1 and 100
            return random.nextInt(100) + 1;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
        //return null;
    }
}