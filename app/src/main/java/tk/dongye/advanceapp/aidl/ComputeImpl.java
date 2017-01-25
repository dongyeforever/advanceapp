package tk.dongye.advanceapp.aidl;

import android.os.RemoteException;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 10:52
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
