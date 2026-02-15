package com.example.aidlrandomservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;

public class MyRandomService extends Service {
    private final Random random = new Random();

    private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public int getRandomNumber() {
             return random.nextInt(100) + 1;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}