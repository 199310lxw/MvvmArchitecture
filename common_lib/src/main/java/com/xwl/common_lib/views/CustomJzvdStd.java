package com.xwl.common_lib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.xwl.common_lib.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

/**
 * @author lxw
 * @date 2023/10/10
 * descripe
 */
public class CustomJzvdStd extends JzvdStd {
    public ImageView shareButton;
    public ImageView play;

    public CustomJzvdStd(Context context) {
        super(context);
    }

    public CustomJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        shareButton = findViewById(R.id.share);
        play = findViewById(R.id.play);
        shareButton.setOnClickListener(this);
        play.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_std;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.share) {
            Toast.makeText(getContext(), "Whatever the icon means", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.play) {
            clickPlay();
        }
    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();
        shareButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        titleTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        shareButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void gotoFullscreen() {
        super.gotoFullscreen();
        titleTextView.setVisibility(View.VISIBLE);

    }

    @Override
    public void gotoNormalScreen() {
        super.gotoNormalScreen();
        titleTextView.setVisibility(View.INVISIBLE);
    }

    protected void clickPlay() {
        Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
        if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
            Toast.makeText(getContext(), getResources().getString(R.string.video_url_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (state == STATE_NORMAL) {
            if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                    jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {//这个可以放到std中
                showWifiDialog();
                return;
            }
            startVideo();
        } else if (state == STATE_PLAYING) {
            Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
            mediaInterface.pause();
            onStatePause();
        } else if (state == STATE_PAUSE) {
            mediaInterface.start();
            onStatePlaying();
        } else if (state == STATE_AUTO_COMPLETE) {
            startVideo();
        }
    }

    //播放完成不自动退出全屏
    @Override
    public void onCompletion() {
        if (screen == SCREEN_FULLSCREEN) {
            onStateAutoComplete();
        } else {
            super.onCompletion();
        }
        posterImageView.setVisibility(View.GONE);
    }

    public void updateStartImage() {
        if (state == STATE_PLAYING) {
            startButton.setVisibility(GONE);
            play.setImageResource(R.drawable.icon_video_pause);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_ERROR) {
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_AUTO_COMPLETE) {
            startButton.setVisibility(VISIBLE);
            play.setImageResource(R.drawable.icon_replay);
            replayTextView.setVisibility(VISIBLE);
        } else if (state == STATE_PAUSE) {
            play.setImageResource(R.drawable.icon_video_play);
            startButton.setVisibility(GONE);
            replayTextView.setVisibility(GONE);
        } else {
            play.setImageResource(R.drawable.icon_video_play);
            startButton.setVisibility(VISIBLE);
            replayTextView.setVisibility(GONE);
        }
    }

    public void gotoTinyScreen() {
        Log.i(TAG, "startWindowTiny " + " [" + this.hashCode() + "] ");
        if (state == STATE_NORMAL || state == STATE_ERROR || state == STATE_AUTO_COMPLETE)
            return;
        ViewGroup vg = (ViewGroup) getParent();
        jzvdContext = vg.getContext();
        blockLayoutParams = getLayoutParams();
        blockIndex = vg.indexOfChild(this);
        blockWidth = getWidth();
        blockHeight = getHeight();

        vg.removeView(this);
        cloneAJzvd(vg);
        CONTAINER_LIST.add(vg);

        ViewGroup vgg = (ViewGroup) (JZUtils.scanForActivity(getContext())).getWindow().getDecorView();//和他也没有关系
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(400, 400);
        lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        //添加滑动事件等
        vgg.addView(this, lp);
        setScreenTiny();
    }
}
