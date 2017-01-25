package tk.dongye.advanceapp.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import tk.dongye.advanceapp.util.LogUtil;

/**
 * description: 连接池具体实现
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 10:58
 */
public class BinderPool {
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private Context context;
    private IBinderPool iBinderPool;
    private static volatile BinderPool sInstance;
//    private CountDownLatch connectBinderPoolCountDownLatch;

    private BinderPool(Context context) {
        this.context = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {
//        connectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(context, BinderPoolService.class);
        context.bindService(service, binderPoolConnection, Context.BIND_AUTO_CREATE);
//        try {
//            connectBinderPoolCountDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (iBinderPool != null) {
                binder = iBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection binderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBinderPool = IBinderPool.Stub.asInterface(iBinder);
//            try {
//                iBinderPool.asBinder().linkToDeath(binderPoolDeathRecipient, 0);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            connectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IBinder.DeathRecipient binderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.e("binder died.");
            iBinderPool.asBinder().unlinkToDeath(binderPoolDeathRecipient, 0);
            iBinderPool = null;
            connectBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {
        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            LogUtil.e(binderCode + "...");
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
