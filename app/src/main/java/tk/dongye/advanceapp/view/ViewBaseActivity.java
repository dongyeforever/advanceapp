package tk.dongye.advanceapp.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import butterknife.BindView;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.ui.BaseActivity;
import tk.dongye.advanceapp.util.LogUtil;

/**
 * description: View基础知识
 * author： dongyeforever@gmail.com
 * date: 2017-02-16 18:06
 */
public class ViewBaseActivity extends BaseActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.activity_main)
    LinearLayout layout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                layout.scrollTo(0 - 100, 0 - 100);
                layout.scrollBy(0 - 100, 0 - 100);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float left = bt2.getX();
                ObjectAnimator.ofFloat(bt2, "translationX", left, left + 100).setDuration(100).start();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTrackerDemo(event);
        return super.onTouchEvent(event);
    }

    /**
     * View的参数坐标：top, left, right, bottom
     * 这些坐标都是相对于View的父容器来说的
     * 获取方法：
     * Left = getLeft()
     * Top = getTop()
     * <p>
     * TouchSlop、VelocityTracker、Scroller
     */

    // VelocityTracker使用实例
    private void velocityTrackerDemo(MotionEvent event) {
        // TouchSlop
        int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        LogUtil.e("touchSlop>>>" + touchSlop);

        // 在View的onTouchEvent方法中追踪当前单击事件的速度
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        // 先计算速度
        velocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        LogUtil.e(xVelocity + " , " + yVelocity);

        // 释放
        velocityTracker.clear();
        velocityTracker.recycle();
    }

    // View的滑动
    class ScrollerView extends View {
        private Scroller mScroller;

        public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mScroller = new Scroller(getContext());
        }

        public ScrollerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ScrollerView(Context context) {
            super(context);
        }

        private void smoothScrollTo(int destX, int destY) {
            // 缓慢滚动到指定的位置
            int scrollX = getScrollX();
            int delta = destX = scrollX;
            // 1000ms滑向destX，效果就是慢慢滑动
            mScroller.startScroll(scrollX, 0, delta, 0, 1000);
            invalidate();

        }

        @Override
        public void computeScroll() {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                postInvalidate();
            }
        }
    }

}
