package com.example.administrator.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 服务端
 */

public class IRemoteService extends Service {

    private static final String TAG = "huangchixing";

    public IRemoteService(){
        Log.d(TAG, "IRemoteService:--- ");
    }

    /**
     * 当客户端绑定到该服务时，该方法会执行
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        //实现这个接口
        @Override
        public int add(int num1, int num2) throws RemoteException {

            Log.d(TAG, "收到了远程的请求，输入参数是 "+num1+"和"+num2);
            return num1 + num2;
        }
    };
}
