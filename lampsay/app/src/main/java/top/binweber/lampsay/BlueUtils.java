package top.binweber.lampsay;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by wangb on 2017/12/24.
 */

public class BlueUtils {

    private final BluetoothDevice mBluetoothDevice;

    private Handler mHandler;

    private BluetoothSocket mBluetoothSocket;

    public BlueUtils(BluetoothDevice device){
        this.mBluetoothDevice = device;
    }

    public void initSocket() {
        BluetoothSocket temp = null;
        try{
            Method m = mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            temp = (BluetoothSocket) m.invoke(mBluetoothDevice, 1);
        }catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        mBluetoothSocket = temp;
    }

    public void doPair() {
        if(mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("other_thread");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }

        mHandler.post(new Runnable() {
            @Override
            public void run(){
                initSocket();
                try {
                    mBluetoothSocket.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.d("BlueUtils", e.getMessage());
        }
    }

}
