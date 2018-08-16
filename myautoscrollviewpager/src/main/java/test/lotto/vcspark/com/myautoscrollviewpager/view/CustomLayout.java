package test.lotto.vcspark.com.myautoscrollviewpager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import test.lotto.vcspark.com.myautoscrollviewpager.R;

/**
 * Created by ytx on 2016/10/28.
 */
public class CustomLayout extends FrameLayout {
    private float ratio;
    public CustomLayout(Context context) {
        super(context);
    }
    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout);
        ratio = ta.getFloat(R.styleable.CustomLayout_rario, -1);
        ta.recycle();

    }
    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);// 获取宽度值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 获取宽度模式
        int height;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 获取高度模式
        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY && ratio > 0) {
            int img_width  = width - getPaddingLeft() - getPaddingRight();
            int img_height  = (int) (img_width / ratio + 0.5f);
            height = img_height  + getPaddingTop() + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }
        // 按照最新的高度测量控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
