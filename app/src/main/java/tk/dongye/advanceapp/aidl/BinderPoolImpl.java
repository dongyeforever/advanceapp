package tk.dongye.advanceapp.aidl;

import android.os.IBinder;
import android.os.RemoteException;

import tk.dongye.advanceapp.util.LogUtil;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 16:30
 */
public class BinderPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        LogUtil.e(binderCode + "...");
        IBinder binder = null;
        switch (binderCode) {
            case BinderPool.BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BinderPool.BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
            default:
                break;
        }
        return binder;
    }
}
