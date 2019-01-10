package com.example.timlv.remoteservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    IGetNameService iGetNameService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        Button btnStartService = (Button)findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startServiceIntent = new Intent(MainActivity.this,MyRemoteService.class);
                startService(startServiceIntent);
            }
        });
        Button btnGetString = (Button)findViewById(R.id.btn_getString);
        btnGetString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindServiceIntent = new Intent(MainActivity.this,MyRemoteService.class);
                ServiceConnection connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        iGetNameService = IGetNameService.Stub.asInterface(service);
                        try{
                            String text = iGetNameService.GetName();
                            EditText displayText = (EditText)findViewById(R.id.txt_display);
                            displayText.setText(text.toCharArray(),0,text.length());
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
                bindService(bindServiceIntent,connection,BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent stopServiceIntent = new Intent(MainActivity.this,MyRemoteService.class);
        stopService(stopServiceIntent);
    }
}
