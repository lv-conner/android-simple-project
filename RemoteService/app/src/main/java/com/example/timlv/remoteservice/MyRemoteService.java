package com.example.timlv.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyRemoteService extends Service {
    public MyRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return _binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyRemoteServiceStart", "onCreate: MyRemoteService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private IGetNameService.Stub _binder = new IGetNameService.Stub() {
        @Override
        public String GetName() throws RemoteException {
            return "Hello this is return from remote service";
        }
    };
}
