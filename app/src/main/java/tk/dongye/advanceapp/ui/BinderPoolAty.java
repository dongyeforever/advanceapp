package tk.dongye.advanceapp.ui;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.aidl.BinderPool;
import tk.dongye.advanceapp.aidl.ComputeImpl;
import tk.dongye.advanceapp.aidl.ICompute;
import tk.dongye.advanceapp.aidl.ISecurityCenter;
import tk.dongye.advanceapp.aidl.SecurityCenterImpl;
import tk.dongye.advanceapp.util.LogUtil;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 11:38
 */
public class BinderPoolAty extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);
        String msg = "hello-world-安卓";
        try {
            String passwd = securityCenter.encrypt(msg);
            LogUtil.i("encrypt: " + passwd);
            LogUtil.i("decrypt: " + securityCenter.decrypt(passwd));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5=" + compute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
