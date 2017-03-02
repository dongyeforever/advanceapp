package tk.dongye.advanceapp.view;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import tk.dongye.advanceapp.R;
import tk.dongye.advanceapp.ui.BaseActivity;
import tk.dongye.advanceapp.util.LogUtil;

/**
 * description: 滑动冲突
 * author： dongyeforever@gmail.com
 * date: 2017-02-20 11:28
 */
public class ViewScrollDemo extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.lv2)
    ListView lv2;
    @BindView(R.id.lv3)
    ListView lv3;
    @BindView(R.id.layout_scroll)
    HorizontalScrollViewEx scroll;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll;
    }

    @Override
    protected void initView() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("name " + i);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.content_list_name, R.id.name, data);
        lv.setAdapter(adapter);
        lv2.setAdapter(adapter);
        lv3.setAdapter(adapter);
    }


}
