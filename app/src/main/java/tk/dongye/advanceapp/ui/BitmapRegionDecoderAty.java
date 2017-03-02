package tk.dongye.advanceapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.util.LogUtil;

/**
 * description:
 * author： dongyeforever@gmail.com
 * date: 2017-03-02 17:24
 */
public class BitmapRegionDecoderAty extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    private BitmapRegionDecoder decoder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_large_img;
    }

    @Override
    protected void initView() {
        super.initView();
        InputStream is;
        try {
            is = getAssets().open("world.jpg");

            // 获得图片宽高
            BitmapFactory.Options tempOptions = new BitmapFactory.Options();
            tempOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tempOptions);
            int width = tempOptions.outWidth;
            int height = tempOptions.outHeight;
            LogUtil.e(width + "," + height);

            decoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = decoder.decodeRegion(new Rect(width / 2 - 200, height / 2 - 200, width / 2, height / 2), options);
            LogUtil.e(bitmap.toString());
            iv.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
