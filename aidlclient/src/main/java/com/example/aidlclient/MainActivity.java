package com.example.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myaidl.IMyAidlInterface;

/**
 * 客户端
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtNum3;

    private Button mButtonAdd;
    IMyAidlInterface iMyAidlInterface;

    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //回收资源
            iMyAidlInterface=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    //软件一启动，就绑定服务
        bindService();
    }

    private void initView() {
        mEtNum1 = (EditText) findViewById(R.id.et_num1);
        mEtNum2 = (EditText) findViewById(R.id.et_num2);
        mEtNum3 = (EditText) findViewById(R.id.et_result);

        mButtonAdd = (Button) findViewById(R.id.btn_add);
        mButtonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int num1 = Integer.parseInt(mEtNum1.getText().toString());
        int num2 = Integer.parseInt(mEtNum2.getText().toString());
        Log.d("huangchixing", "num1= "+num1);
        Log.d("huangchixing", "num2= "+num2);
        try {
            //调用远程的服务

            int result = iMyAidlInterface.add(num1,num2);
            Log.d("huangchixing", "result: "+result);
            mEtNum3.setText(result+"");
        } catch (RemoteException e) {
            e.printStackTrace();
            mEtNum3.setText("错误了");
        }

    }

    private void bindService() {
        //获取到服务端
        Intent intent = new Intent();
        //新版本必须显示intent启动绑定服务
        intent.setComponent(new ComponentName("com.example.administrator.myaidl","com.example.administrator.myaidl.IRemoteService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
