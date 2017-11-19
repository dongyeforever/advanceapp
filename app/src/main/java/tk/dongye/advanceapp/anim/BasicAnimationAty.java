package tk.dongye.advanceapp.anim;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.ui.BaseActivity;

/**
 * description: 基础动画
 * author： dongyeforever@gmail.com
 * date: 2017-07-26 10:16
 */
public class BasicAnimationAty extends BaseActivity {
    @BindView(R.id.btn)
    Button button;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_anim;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.btn)
    public void click(View view) {
        performAnimate();
    }

    private void performAnimate() {
       ObjectAnimator.ofInt(button, "width", 500).setDuration(3000).start();
    }
}
