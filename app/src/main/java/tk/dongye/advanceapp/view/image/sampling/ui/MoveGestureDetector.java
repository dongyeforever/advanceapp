package tk.dongye.advanceapp.view.image.sampling.ui;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-03-03 15:28
 */
public class MoveGestureDetector extends BaseGestureDetector {
    private PointF mCurrentPointer;
    private PointF mPrePointer;
    private PointF mDeltaPointer = new PointF();

    private PointF mExtenalPointer = new PointF();
    private OnMoveGestureListener mListener;

    public MoveGestureDetector(Context mContext, OnMoveGestureListener mListener) {
        super(mContext);
        this.mListener = mListener;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListener.onMoveEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean update = mListener.onMove(this);
                if (update) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                resetState(); // 防止没有接收到CANCEL or UP ,保险起见
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        final MotionEvent prev = mPreMotionEvent;

        mPrePointer = calculateFocalPointer(prev);
        mCurrentPointer = calculateFocalPointer(event);

        boolean mSkipThisMoveEvent = prev.getPointerCount() != event.getPointerCount();
        mExtenalPointer.x = mSkipThisMoveEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
        mExtenalPointer.y = mSkipThisMoveEvent ? 0 : mCurrentPointer.y - mPrePointer.y;
    }

    public float getMoveX() {
        return mExtenalPointer.x;
    }

    public float getMoveY() {
        return mExtenalPointer.y;
    }

    /**
     * 根据event计算多指中心点
     *
     * @param event event
     */
    private PointF calculateFocalPointer(MotionEvent event) {
        final int count = event.getPointerCount();
        float x = 0, y = 0;
        for (int i = 0; i < count; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= count;
        y /= count;

        return new PointF(x, y);
    }

    public interface OnMoveGestureListener {
        boolean onMoveBegin(MoveGestureDetector detector);

        boolean onMove(MoveGestureDetector detector);

        void onMoveEnd(MoveGestureDetector detector);
    }

    public static class SimpleMoveGestureDetector implements OnMoveGestureListener {

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {
        }
    }

}
