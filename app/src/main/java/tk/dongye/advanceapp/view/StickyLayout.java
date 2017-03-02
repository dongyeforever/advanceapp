package tk.dongye.advanceapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-02-20 16:49
 */
public class StickyLayout extends LinearLayout {
    private int mTouchSlop;
    private int mLastX;
    private int mLastY;
    // 分别记录上次滑动的坐标（onInterceptTouchEvent）
    private int mLastXIntercept;
    private int mLastYIntercept;
    private boolean mDisallowInterceptTouchEventOnHeader;

    public StickyLayout(Context context) {
        super(context);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int intercepted = 0;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercept = x;
                mLastYIntercept = y;
                mLastX = x;
                mLastY = y;

                intercepted = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (mDisallowInterceptTouchEventOnHeader && y <= getHeaderHeight()) {
                    intercepted = 0;
                }else if (Math.abs(deltaY) <= Math.abs(deltaX)) {
                    intercepted = 0;
                }

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private int getHeaderHeight() {
        return 0;
    }
}
