package com.example.timlv.remoteserviceclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.timlv.remoteservice.*;

public class MainActivity extends AppCompatActivity {
    IGetNameService iGetNameService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnInvokeService = (Button)findViewById(R.id.btn_invoke_service);
        btnInvokeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindServiceIntent = new Intent();
                bindServiceIntent.setAction("com.example.timlv.remoteservice.MyRemoteService");
                bindServiceIntent.setPackage("com.example.timlv.remoteservice");
                bindService(bindServiceIntent,connection,BIND_AUTO_CREATE);
            }
        });
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iGetNameService = IGetNameService.Stub.asInterface(service);
            try
            {
                String txt = iGetNameService.GetName();
                EditText editText = (EditText)findViewById(R.id.txt_display);
                editText.setText(txt.toCharArray(),0,txt.length());

            }
            catch (RemoteException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iGetNameService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
