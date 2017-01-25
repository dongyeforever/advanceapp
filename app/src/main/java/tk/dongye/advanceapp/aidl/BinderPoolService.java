package tk.dongye.advanceapp.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import tk.dongye.advanceapp.util.LogUtil;

/**
 * description: binder连接池
 * author： dongyeforever@gmail.com
 * date: 2017-01-25 10:56
 */
public class BinderPoolService extends Service {
    private Binder binderPool ;

    @Override
    public void onCreate() {
        super.onCreate();
        binderPool = new BinderPool.BinderPoolImpl();
        LogUtil.e(binderPool.toString());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind");
        return binderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
