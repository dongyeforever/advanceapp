package tk.dongye.advanceapp.ui;

import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.view.imageview.ImageSource;
import tk.dongye.advanceapp.view.imageview.SubsamplingScaleImageView;

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
        iv.setImage(ImageSource.asset("aaa.jpg"));
    }

    @OnClick(R.id.iv_sampling)
    public void click(View v) {
        Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.iv_sampling)
    public boolean longClick(View v) {
        Toast.makeText(this, "long click...", Toast.LENGTH_SHORT).show();
        return true;
    }

}
