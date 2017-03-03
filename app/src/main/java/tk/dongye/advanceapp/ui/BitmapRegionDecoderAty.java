package tk.dongye.advanceapp.ui;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.view.LargeImageView;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-03-02 17:24
 */
public class BitmapRegionDecoderAty extends BaseActivity {
    @BindView(R.id.iv)
    LargeImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_large_img;
    }

    @Override
    protected void initView() {
        super.initView();

        try {
            InputStream is = getAssets().open("aaa.jpg");
            iv.setInputStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
