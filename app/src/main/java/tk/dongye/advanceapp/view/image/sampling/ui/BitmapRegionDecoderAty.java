package tk.dongye.advanceapp.view.image.sampling.ui;

import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.ui.BaseActivity;
import tk.dongye.advanceapp.view.image.sampling.ImageSource;
import tk.dongye.advanceapp.view.image.sampling.SubsamplingScaleImageView;


/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-03-02 17:24
 */
public class BitmapRegionDecoderAty extends BaseActivity {
    //    @BindView(R.id.iv)
//    LargeImageView iv;
    @BindView(R.id.iv_sampling)
    SubsamplingScaleImageView iv;
    private GestureDetector gestureDetector;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_large_img;
    }

    @Override
    protected void initView() {
        super.initView();

//        try {
//            InputStream is = getAssets().open("aaa.jpg");
//            iv.setInputStream(is);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

         gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (iv.isReady()) {
                    PointF sCoord = iv.viewToSourceCoord(e.getX(), e.getY());
                    Toast.makeText(getApplicationContext(), "Single tap: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Single tap: Image not ready", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (iv.isReady()) {
                    PointF sCoord = iv.viewToSourceCoord(e.getX(), e.getY());
                    Toast.makeText(getApplicationContext(), "Long press: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Long press: Image not ready", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (iv.isReady()) {
                    PointF sCoord = iv.viewToSourceCoord(e.getX(), e.getY());
                    Toast.makeText(getApplicationContext(), "Double tap: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Double tap: Image not ready", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        iv.setImage(ImageSource.asset("aaa.jpg"));
    }

    @OnTouch(R.id.iv_sampling)
    public boolean touch(View v, MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }

    @OnClick(R.id.iv_sampling)
    public void click(View v) {
//        finish();
        Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.iv_sampling)
    public boolean longClick(View v) {
        Toast.makeText(this, "long click...", Toast.LENGTH_SHORT).show();
        return true;
    }

}
