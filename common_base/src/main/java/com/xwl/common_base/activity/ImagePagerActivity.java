package com.xwl.common_base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.xwl.common_base.R;
import com.xwl.common_base.databinding.ActivityImagePagerBinding;
import com.xwl.common_base.viewmodel.EmptyViewModel;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * author: Administrator
 * time: 2022/8/19
 * desc: 查看大图
 */
public class ImagePagerActivity extends BaseVmVbActivity<EmptyViewModel, ActivityImagePagerBinding> {
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";

    public static void startImagePagerActivity(Activity activity, List<String> imgUrls, int position) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        activity.startActivity(intent);
    }

    private final List<View> guideViewList = new ArrayList<>();


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

        ViewPager viewPager = findViewById(R.id.view_pager);

        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        int size = imgUrls.size();

        ImageAdapter adapter = new ImageAdapter(this);
        adapter.setDatas(imgUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!guideViewList.isEmpty() && position < guideViewList.size()) {
                    for (int i = 0; i < guideViewList.size(); i++) {
                        guideViewList.get(i).setSelected(i == position);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        LinearLayout guideGroup = findViewById(R.id.ll_guide);
        if (size > 1) {
            addGuideView(guideGroup, startPos, imgUrls);
        }
        guideGroup.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void initData() {

    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (!imgUrls.isEmpty()) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.dimen_size_dp_6),
                        getResources().getDimensionPixelSize(R.dimen.dimen_size_dp_6));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    /**
     * 监听返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ImagePagerActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<>();
        private final LayoutInflater inflater;
        private final Context context;

        public void setDatas(List<String> datas) {
            if (datas != null) {
                this.datas = datas;
            }
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }

        @NonNull
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);

            FrameLayout flItem = view.findViewById(R.id.fl_item);
            final PhotoView photoView = view.findViewById(R.id.photo_view);

            String url = datas.get(position);
            //单击图片退出
            photoView.setOnPhotoTapListener((view1, x, y) -> ImagePagerActivity.this.finish());
            photoView.setOnViewTapListener((view12, x, y) -> ImagePagerActivity.this.finish());

            flItem.setOnTouchListener((v, event) -> {
                ImagePagerActivity.this.finish();
                return true;
            });

            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url)
                        .placeholder(com.xwl.common_lib.R.mipmap.default_img) // 占位符，异常时显示的图片
                        .error(com.xwl.common_lib.R.mipmap.default_img) // 错误时显示的图片
//                        .skipMemoryCache(false) //启用内存缓存
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //磁盘缓存策略
                        .into(photoView);
            }

            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
