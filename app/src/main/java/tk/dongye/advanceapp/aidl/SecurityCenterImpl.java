package tk.dongye.advanceapp.aidl;

import android.os.RemoteException;

import tk.dongye.advanceapp.util.LogUtil;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 09:56
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        LogUtil.e("encrypt " + Thread.currentThread().getName());
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
