package tk.dongye.advanceapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import tk.dongye.advanceapp.util.LogUtil;


/**
 * description: 水平滚动的ViewGroup
 * author： dongyeforever@gmail.com
 * date: 2017-02-17 16:34
 */
public class HorizontalScrollViewEx extends HorizontalScrollView {
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept, mLastYIntercept;
    // 上次滑动的坐标
    private int mLastX, mLastY;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("onInterceptTouchEvent ACTION_DOWN >>>");
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("onInterceptTouchEvent ACTION_MOVE >>>");
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 水平滑动
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("onInterceptTouchEvent ACTION_UP >>>");
                intercepted = false;
                break;
        }

        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        LogUtil.e("intercepted>>>" + intercepted);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("onTouchEvent ACTION_DOWN >>>");
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("onTouchEvent ACTION_MOVE >>>");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("onTouchEvent ACTION_UP >>>");
                int scrollX = getScrollX();

                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
