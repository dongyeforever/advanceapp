package tk.dongye.advanceapp.aidl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import tk.dongye.advanceapp.MainActivity;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.util.LogUtil;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-01-22 11:34
 */
public class MyService extends Service {
    private static final String PACKAGE_SAYHI = "tk.dongye.advanceapp";

    private NotificationManager mNotificationManager;
    private boolean mCanRun = true;
    private final List<Book> mBooks = new ArrayList<>();

    // 这里实现了aidl的抽象函数
    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBooks) {
                return mBooks;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBooks) {
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
            }
        }

        //在这里可以做权限认证，return false意味着客户端的调用就会失败，比如下面，只允许包名为tk.dongye.advanceapp的客户端通过，
        //其他apk将无法完成调用过程
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = MyService.this.getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            LogUtil.d("onTransact: " + packageName);

            if (!PACKAGE_SAYHI.equals(packageName)) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        Thread thread = new Thread(null, new ServiceWorker(), "BackgroundService");
        thread.start();

        synchronized (mBooks) {
            for (int i = 0; i < 6; i++) {
                Book book = new Book(i, "哈利波特" + (i + 1));
                mBooks.add(book);
            }
        }

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(String.format("on bind,intent = %s", intent.toString()));
        displayNotificationMessage("服务已启动");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCanRun = false;
    }

    private void displayNotificationMessage(String message) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("我的通知")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;  // 点击通知后通知消失
        mNotificationManager.notify(1, notification);
    }

    class ServiceWorker implements Runnable {
        long counter = 0;

        @Override
        public void run() {
            // do in background processing here...
            while (mCanRun) {
                LogUtil.i("counter: " + counter);
                counter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
