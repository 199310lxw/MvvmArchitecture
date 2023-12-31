package com.xwl.common_lib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.xwl.common_lib.R;
import com.xwl.common_lib.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxw
 * @date 2023/10/27
 * descripe
 */


/**
 * Created by yds
 * on 2020/3/11.
 */
public class ThreeImageView<T> extends ViewGroup {
    private Context mContext;
    private int mGap;//图片间距
    private int mSingleImgSize;//单张图片尺寸
    private int mMaxSize = 3;//最大图片数
    private List<ImageView> mImageViewList = new ArrayList<>();
    private List<T> mImgDataList;
    private int mImageSize;//图片大小
    private int mRowCount;//行数，用于后续扩展
    private int mColumnCount;//列数
    private TextView textView;

    public ThreeImageView(Context context) {
        this(context, null);
    }

    public ThreeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        //自定义xml属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeImageView);
        this.mGap = (int) typedArray.getDimension(R.styleable.ThreeImageView_imgGap, 0);
        this.mSingleImgSize = typedArray.getDimensionPixelSize(R.styleable.ThreeImageView_singleImgSize, -1);
        this.mMaxSize = typedArray.getInteger(R.styleable.ThreeImageView_maxSize, 3);
        typedArray.recycle();
    }

    private void layoutChildrenView() {
        if (mImgDataList == null) return;
        int showChildrenCount = getNeedShowCount(mImgDataList.size());
        layoutMaxCountChildrenView(showChildrenCount);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取总宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //获取总高度
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //控件内容的总宽度
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImgDataList != null && mImgDataList.size() > 0) {
            //如果图片只有一个且单个图片尺寸不为-1
            if (mImgDataList.size() == 1 && mSingleImgSize != -1) {
                //图片尺寸取总宽度与单个图片宽度中的较小值
                mImageSize = Math.min(mSingleImgSize, totalWidth);
            } else {
                //如果图片不止有一个，则图片尺寸为（总宽度-图片间总间距）/图片数量
                mImageSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount;
            }
            //（图片尺寸*行数）+总间距+上内边距+下内边距   设置行数是为后续扩展
            height = (mImageSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom()) * 2 / 3;
        }
        //设置测量的尺寸
        setMeasuredDimension(width, height);
    }


    private void layoutMaxCountChildrenView(int childrenCount) {
        int left = 0, top = 0, right = 0, bottom = 0;
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            childrenView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            left = getPaddingLeft() + i % 3 * mImageSize + i % 3 * mGap;
            top = getPaddingTop() + i / 3 * mImageSize + i / 3 * mGap;
            right = left + mImageSize;
            bottom = top + mImageSize * 2 / 3;
            childrenView.layout(left, top, right, bottom);
            Glide.with(mContext).load(mImgDataList.get(i)).transform(new CenterCrop(), new RoundedCorners(ScreenUtil.dip2px(6))).into(childrenView);
        }
        showImageAndText(left, top, right, bottom);
    }

    private void showImageAndText(int left, int top, int right, int bottom) {
        if (mImgDataList.size() > mMaxSize) {
            if (textView != null) {
                textView.bringToFront();
                //设置字体大小
                String text = "共" + mImgDataList.size() + "张图";
                int textSize = px2sp(mContext, (float) mImageSize / 6);
                textView.setTextSize(textSize);
                textView.setText(text);

                //设置文本颜色及背景半透明
                textView.setTextColor(Color.WHITE);
//                textView.setBackgroundColor(0x80000000);
                textView.setBackgroundResource(R.drawable.shape_thread_image_text_bg);

                //设置文字位置
                // 1.用FontMetrics对象计算高度
                Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
                int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;//文本高度
                int paddingTop = (mImageSize * 2 / 3 - textHeight) / 2;
                //方法一：设置居中,setGravity(Gravity.CENTER)只显示为水平居中，所以需要设置padding
                textView.setPadding(0, paddingTop, 0, 0);
                textView.setGravity(Gravity.CENTER);
                textView.layout(left, top, right, bottom);
                //这里设置只会显示水平居中，所以需要上面的padding

            }
        }
    }

    /**
     * 获取需要显示的数量
     *
     * @param size
     * @return
     */
    private int getNeedShowCount(int size) {
        //如果size大于最大显示数量，则用最大显示数量
        if (mMaxSize > 0 && size > mMaxSize) {
            return mMaxSize;
        } else {
            //如果size小于最大显示数量，则用size显示数量
            return size;
        }
    }

    /**
     * 设置图片数据
     *
     * @param list
     */
    public void setImagesData(List<T> list) {
        mImgDataList = list;
        removeAllViews();
        if (list == null || list.size() == 0) {
            //如果图片列表为空，则隐藏控件
            this.setVisibility(GONE);
        } else {
            //如果图片列表不为空，则显示
            this.setVisibility(VISIBLE);
        }
        //获取需要显示的数量
        assert list != null;
        int showCount = getNeedShowCount(list.size());
        //计算参数，根据需要显示的数量计算行数和列数
        int[] params = calculateParam(showCount);
        mRowCount = params[0];//行数
        mColumnCount = params[1];//列数
        for (int i = 0; i < showCount; i++) {
            ImageView iv = getImageView(i);
            if (iv == null) {
                return;
            }
            addView(iv, generateDefaultLayoutParams());
            if (i == mMaxSize - 1 && list.size() > mMaxSize) {
                textView = new TextView(mContext);
                addView(textView, generateDefaultLayoutParams());
            }
        }
        requestLayout();
    }

    protected int[] calculateParam(int imageSize) {
        int[] params = new int[2];
        params[0] = imageSize / 3 + (imageSize % 3 == 0 ? 0 : 1);
        params[1] = 3;
        return params;
    }

    private ImageView getImageView(final int position) {
        if (position < mImageViewList.size()) {
            return mImageViewList.get(position);
        } else {
            ImageView imageView = new ImageView(mContext);
            mImageViewList.add(imageView);
            return imageView;
        }
    }

    public void setGap(int gap) {
        this.mGap = gap;
    }

    public int getGap() {
        return mGap;
    }

    /**
     * 设置只有一张图片时的尺寸大小
     *
     * @param singleImgSize 单张图片的尺寸大小
     */
    public void setSingleImgSize(int singleImgSize) {
        mSingleImgSize = singleImgSize;
    }

    /**
     * 设置最大图片数
     *
     * @param maxSize 最大图片数
     */
    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    private int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}

