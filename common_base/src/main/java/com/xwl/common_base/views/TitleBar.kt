package com.xwl.common_base.views

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.xwl.common_base.R
import com.xwl.common_base.databinding.LayoutTitleBarBinding
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author: lxw
 * @date:  2023/04/29
 * @desc: 标题控件
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mBinding: LayoutTitleBarBinding

    init {
        init(context, attrs)
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param attrs   attrs
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        mBinding = LayoutTitleBarBinding.inflate(inflater, this, true)

        mBinding.ivLeftIcon.onClick {
            val activity = context as Activity
            activity.finish()
        }
        setAttrs(attrs, array)
    }

    /**
     * 设置属性
     *
     * @param attrs attrs
     * @param array array
     */
    private fun setAttrs(attrs: AttributeSet?, array: TypedArray) {
        if (attrs == null) {
            return
        }
        val count = array.indexCount
        for (i in 0 until count) {
            when (val attr = array.getIndex(i)) {
                R.styleable.TitleBar_middleText -> {
                    val resId = array.getString(attr)
                    mBinding.tvMiddle.text = resId
                }
                R.styleable.TitleBar_rightText -> {
                    val resId = array.getString(attr)
                    mBinding.tvRight.text = resId
                }
                R.styleable.TitleBar_leftIcon -> {
                    val drawable = array.getDrawable(attr)
                    mBinding.ivLeftIcon.setImageDrawable(drawable)
                }
                R.styleable.TitleBar_leftVisible -> {
                    val show = array.getBoolean(attr, true)
                    val visible = if (show) VISIBLE else INVISIBLE
                    mBinding.ivLeftIcon.visibility = visible
                }
                R.styleable.TitleBar_middleTextColor -> {
                    val color = array.getColor(attr, 0)
                    mBinding.tvMiddle.setTextColor(color)
                }
                R.styleable.TitleBar_rightTextColor -> {
                    val color = array.getColor(attr, 0)
                    mBinding.tvRight.setTextColor(color)
                }
                R.styleable.TitleBar_background -> {
                    val color = array.getColor(attr, 0)
                    mBinding.root.setBackgroundColor(color)
                }
                R.styleable.TitleBar_rightTextSize -> {
                    val size = array.getDimension(attr, 0f)
                    mBinding.tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
                }
                R.styleable.TitleBar_middleTextBold -> {
                    val bold = array.getBoolean(attr, false)
                    val paint = mBinding.tvMiddle.paint
                    paint.isFakeBoldText = bold
                }
                R.styleable.TitleBar_rightTextBold -> {
                    val bold = array.getBoolean(attr, false)
                    val paint = mBinding.tvRight.paint
                    paint.isFakeBoldText = bold
                }
                R.styleable.TitleBar_rightVisible -> {
                    val show = array.getBoolean(attr, true)
                    val visible = if (show) VISIBLE else INVISIBLE
                    mBinding.tvRight.visibility = visible
                }
                R.styleable.TitleBar_leftVisible -> {
                    val visible = if (array.getBoolean(attr, true)) VISIBLE else INVISIBLE
                    mBinding.ivLeftIcon.visibility = visible
                }
                R.styleable.TitleBar_showDividerLine -> {
                    val show = array.getBoolean(attr, false)
                    val visible = if (show) VISIBLE else GONE
                    mBinding.dividerLine.visibility = visible
                }
                R.styleable.TitleBar_fitsSystemWindows -> {
                    val show = array.getBoolean(attr, false)
                    val top = if (show) 0 else ScreenUtil.dip2px(28f)
                    mBinding.root.setPadding(0, top, 0, 0)
                }
                R.styleable.TitleBar_bottomLineVisible -> {
                    val isLineVisible = array.getBoolean(attr, false)
                    mBinding.dividerLine.visibility = if (isLineVisible) View.VISIBLE else View.GONE
                }
            }
        }
        array.recycle()
    }

    fun setMiddleText(title: String?) {
        mBinding.tvMiddle.text = title
    }

    fun setMiddleText(resId: Int) {
        mBinding.tvMiddle.setText(resId)
    }

    fun getTitleRootView(): FrameLayout {
        return mBinding.root
    }

    fun getMiddleTextView(): TextView {
        return mBinding.tvMiddle
    }

    fun getRightTextView(): TextView {
        return mBinding.tvRight
    }

    fun getLeftImageView(): ImageView {
        return mBinding.ivLeftIcon
    }


}