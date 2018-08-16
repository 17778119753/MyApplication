package test.lotto.vcspark.com.myautoscrollviewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import test.lotto.vcspark.com.myautoscrollviewpager.utils.ImgUtils;

public class BannerAdapter extends PagerAdapter {

    public static final String TAG = BannerAdapter.class.getSimpleName();
    private List<String> adList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public BannerAdapter(Context mContext, List<String> adList) {
        this.adList = adList;
        context = mContext;
        options = ImgUtils.mImageOptions();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int pos = position % adList.size();
        Log.e(TAG, "position = " + position + "      pos = " + pos);
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageLoader.displayImage(adList.get(pos), imageView, options);
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了第"+(pos+1)+"张图片",Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
