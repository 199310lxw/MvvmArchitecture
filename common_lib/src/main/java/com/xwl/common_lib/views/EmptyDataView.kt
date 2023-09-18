package com.xwl.common_lib.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.xwl.common_lib.R
import com.xwl.common_lib.databinding.ViewEmptyDataBinding

/**
 * @author lxw
 * @date 2022/01/11 16:11
 * @desc 空数据view
 */
class EmptyDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mBinding: ViewEmptyDataBinding

    private var emptyAnimationPath: String = "empty_data.json"
    private var mEmptyText: String = resources.getString(R.string.default_no_data)
    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mBinding = ViewEmptyDataBinding.inflate(LayoutInflater.from(context), this, true)
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyDataView)
            val emptyText = ta.getString(R.styleable.EmptyDataView_emptyText)
            val emptyAnim = ta.getString(R.styleable.EmptyDataView_emptyAnim)
            val bgColor = ta.getColor(
                R.styleable.EmptyDataView_bg_color,
                ContextCompat.getColor(context, R.color.white)
            )
            val emptyPaddingBottom = ta.getDimension(
                R.styleable.EmptyDataView_emptyPaddingBottom,
                0.0f
            ).toInt()

            mBinding.tvNoData.text =if(!emptyText.isNullOrEmpty()) emptyText else mEmptyText
            mBinding.lottieEmpty.setAnimation(if(!emptyAnim.isNullOrEmpty()) emptyAnim else emptyAnimationPath)
            setBackgroundColor(bgColor)
            setPadding(0, 0, 0, emptyPaddingBottom)

            ta.recycle()
        }
    }

    /**
     * 设置背景颜色
     *
     * @param colorId 颜色值
     *
     * @return
     */
    fun setBgColor(colorId: Int): EmptyDataView {
        setBackgroundColor(colorId)
        return this
    }

    /**
     * 设置空视图icon
     *
     * @param resId 图片id
     *
     * @return
     */

    fun setAnimPath(path: String) {
        if(path.isNullOrEmpty() || !path.endsWith(".json")) return
        mBinding.lottieEmpty.setAnimation(path)
    }

    /**
     * 设置提示文字
     *
     * @param str 内容
     *
     * @return
     */
    fun setText(str: String?): EmptyDataView {
        mBinding.tvNoData.text = str
        return this
    }
}
