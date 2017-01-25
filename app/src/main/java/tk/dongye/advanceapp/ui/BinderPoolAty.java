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
        doWork();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);
        LogUtil.d("visit ISecurityCenter");
        String msg = "hello-world-安卓";
        System.out.println("count: " + msg);
        try {
            String passwd = securityCenter.encrypt(msg);
            System.out.println("encrypt: " + passwd);
            System.out.println("decrypt: " + securityCenter.decrypt(passwd));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        LogUtil.d("visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5=" + compute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
