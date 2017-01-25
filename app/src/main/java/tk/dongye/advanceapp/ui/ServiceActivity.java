package tk.dongye.advanceapp.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.aidl.Book;
import tk.dongye.advanceapp.aidl.IBookManager;
import tk.dongye.advanceapp.aidl.IOnNewBookArrivedListener;
import tk.dongye.advanceapp.util.LogUtil;

public class ServiceActivity extends AppCompatActivity {
    private static final String ACTION_BIND_SERVICE = "tk.dongye.advanceapp.MyService";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBookManager mIBookManager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    LogUtil.i("arrived new book " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    // 死亡代理
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mIBookManager == null) {
                return;
            }
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBookManager = null;
            // 重新绑定
            bindService();
        }
    };

    // 自动通知
    private IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e("pid:  " + Process.myPid());
            // 通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIBookManager = IBookManager.Stub.asInterface(iBinder);

            try {
                // 设置死亡代理
                iBinder.linkToDeath(mDeathRecipient, 0);
                mIBookManager.registerListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIBookManager = null;
            LogUtil.e("binder died.");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind service
        bindService();

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Book> books = mIBookManager.getBookList();

                    LogUtil.e(books.toString());
                    showDialog(books.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mIBookManager.addBook(new Book(-1, "哈哈"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindService() {
        Intent intentService = new Intent(ACTION_BIND_SERVICE);
        intentService.setPackage(getPackageName());
        intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ServiceActivity.this.bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
    }

    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("scott")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (mIBookManager != null && mIBookManager.asBinder().isBinderAlive()) {
            try {
                LogUtil.i("unregister listener: " + iOnNewBookArrivedListener);
                mIBookManager.unregisterListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }

}
