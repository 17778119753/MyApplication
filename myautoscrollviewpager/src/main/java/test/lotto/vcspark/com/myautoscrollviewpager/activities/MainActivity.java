package test.lotto.vcspark.com.myautoscrollviewpager.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.lotto.vcspark.com.myautoscrollviewpager.R;
import test.lotto.vcspark.com.myautoscrollviewpager.adapter.BannerAdapter;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager auto_viewpager;
    private List<String> mADParseArray;
    private final int HOME_AD_RESULT = 1;
    private TextView tv_content;
    private LinearLayout dotLayout; // 圈圈 布局
    private Context mContext;
    private String[] content;
    
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 广告
                case HOME_AD_RESULT:
                    auto_viewpager.setCurrentItem(auto_viewpager.getCurrentItem() + 1,
                            true);
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContext = MainActivity.this;
        auto_viewpager = (ViewPager)findViewById(R.id.vp_shuffling);
        tv_content = (TextView) findViewById(R.id.tv_content);
        dotLayout = (LinearLayout)findViewById(R.id.layout_point);
        mADParseArray = new ArrayList<String>();
        mADParseArray
                .add("http://m.easyto.com/m/zhulifuwu_banner.jpg");
        mADParseArray
                .add("http://m.easyto.com/m/japan/images/banner_3y_new.jpg");
        mADParseArray
                .add("http://m.easyto.com/m/japan/images/banner_5y_new.jpg");
        final int size = mADParseArray.size();
        content = new String[]{"海外助理服务，抢先体验","日本个签1799元三年多次","日本个签1999元五年多次"};
        auto_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * @param position:跳转完毕的页码角标，这个方法做自动轮播
             */
            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"onPageSelected");
                refreshPoint(position % size);
                if (mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }
                mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);
            }

            /**
             * @param arg0:滑动时代表当前页角标，滑动结束时代表滑动停止时的页码角标
             * @param arg1：0-1或者1-0变化
             * @param arg2：0 <-> 屏幕的宽度值变化
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.e(TAG,"onPageScrolled");
            }

            /**
             * @param arg0:0 没滑动  1：正在滑动  2：滑动完毕
             */
            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.e(TAG,"onPageScrollStateChanged = "+arg0);
                if (ViewPager.SCROLL_STATE_DRAGGING == arg0
                        && mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }
            }
        });
        BannerAdapter adapter = new BannerAdapter(mContext, mADParseArray);
        auto_viewpager.setAdapter(adapter);

        initPointsLayout(size);
        auto_viewpager.setCurrentItem(size * 1000, false);//设置当前position，不设置开始无法右滑
        // 自动轮播线程
        mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);
    }

    private void initPointsLayout(int size) {
        for (int i = 0; i < size; i++) {
            ImageView image = null;
            if (mContext != null) {
                image = new ImageView(mContext);
            }
            float denstity = getResources().getDisplayMetrics().density;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (6 * denstity), (int) (6 * denstity));
            params.leftMargin = (int) (2 * denstity);
            params.rightMargin = (int) (2 * denstity);
            image.setLayoutParams(params);
            if (i == 0) {
                image.setBackgroundResource(R.mipmap.dot_enable);
            } else {
                image.setBackgroundResource(R.mipmap.dot_normal);
            }
            dotLayout.addView(image);
        }
    }

    private void refreshPoint(int position) {
        if (dotLayout != null) {
            for (int i = 0; i < dotLayout.getChildCount(); i++) {
                if (i == position) {
                    dotLayout.getChildAt(i).setBackgroundResource(
                            R.mipmap.dot_enable);
                    tv_content.setText(content[position]);
                } else {
                    dotLayout.getChildAt(i).setBackgroundResource(
                            R.mipmap.dot_normal);
                }
            }
        }
    }

}
