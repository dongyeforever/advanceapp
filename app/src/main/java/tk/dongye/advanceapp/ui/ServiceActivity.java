package tk.dongye.advanceapp.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.aidl.Book;
import tk.dongye.advanceapp.aidl.IBookManager;

public class ServiceActivity extends AppCompatActivity {
    private static final String ACTION_BIND_SERVICE = "tk.dongye.advanceapp.MyService";
    private IBookManager mIBookManager;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                Book book = mIBookManager.getBookList().get(0);
                showDialog(book.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService = new Intent(ACTION_BIND_SERVICE);
                intentService.setPackage(getPackageName());
                intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ServiceActivity.this.bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        if (mIBookManager != null) {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }

}
