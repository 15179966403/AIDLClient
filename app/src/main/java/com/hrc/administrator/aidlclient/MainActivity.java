package com.hrc.administrator.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hrc.administrator.aidlactivity.IMyService;

public class MainActivity extends Activity implements View.OnClickListener{

    private IMyService myService=null;
    private Button btnInvokeAIDLService;
    private Button btnBindAIDLSercice;
    private TextView textView;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService=IMyService.Stub.asInterface(service);
            btnInvokeAIDLService.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInvokeAIDLService=(Button)findViewById(R.id.btnInvokeAIDlService);
        btnBindAIDLSercice=(Button)findViewById(R.id.btnBindAIDLService);
        btnInvokeAIDLService.setEnabled(false);
        textView=(TextView)findViewById(R.id.textview);
        btnInvokeAIDLService.setOnClickListener(this);
        btnBindAIDLSercice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBindAIDLService:
                Intent intent=new Intent();
                intent.setAction("com.hrc.administrator.aidlactivity.IMyService");
                intent.setPackage("com.hrc.administrator.aidlactivity");
                bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnInvokeAIDlService:
                try{
                    textView.setText(myService.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
