package tk.dongye.advanceapp.ui;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/**
 * description: 所有activity的基类
 * author: dongyeforever@foxmail.com
 * date: 2016-07-15 15:22
 * version: 1.0
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        // 通过注解绑定控件
        ButterKnife.bind(this);
        init(savedInstanceState);
        initView();
        initData();

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftInput();
    }

    protected void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void onBeforeSetContentLayout() {
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }

    protected void init(Bundle savedInstanceState) {
    }

    //跳转到Activity
    public void toActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    //跳转到Activity 传递参数bundle
    public void toActivity(Class<?> activity, Bundle bundle) {
        Intent i = new Intent(this, activity);
        i.putExtras(bundle);
        startActivity(i);
    }

    //跳转到Activity
    public void toActivity(Class<?> activity, String key, String value) {
        Intent i = new Intent(this, activity);
        i.putExtra(key, value);
        startActivity(i);
    }

    //跳转到Activity
    public void toActivity(Class<?> activity, String key, int value) {
        Intent i = new Intent(this, activity);
        i.putExtra(key, value);
        startActivity(i);
    }

    protected void initView() {
    }

    protected void initData() {
    }

    //联网自动刷新
    protected void autoRefresh() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    public void addFragment(@IdRes int containerId, Fragment fragment, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerId, fragment, tag);
        transaction.commit();
    }

    public void hideFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void replaceFragment(@IdRes int contentId, Fragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(contentId, fragment, tag);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

}
