package com.xwl.common_base.dialog

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.orhanobut.logger.Logger
import com.xwl.common_base.databinding.DialogPickerCalendarBinding
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ScreenUtil
import com.xwl.common_lib.utils.ViewUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author mingyan.su
 * @date   2023/4/24 18:35
 * @desc   生日日期弹框
 */
class SelectBirthdayDialog {
    @RequiresApi(Build.VERSION_CODES.O)
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {

        private val mCurrentData = Calendar.getInstance()

        private var mOnDateCall: ((String?) -> Unit)? = null

        private val mBinding: DialogPickerCalendarBinding =
            DialogPickerCalendarBinding.inflate(LayoutInflater.from(activity))

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM

            ViewUtils.setClipViewCornerTopRadius(mBinding.clRoot, ScreenUtil.dip2px(12f))
//            mBinding.datePicker.setOnDateChangeListener { view, year, month, dayOfMonth ->
//                Logger.i("日期选择回调：$year-$month-$dayOfMonth")
//                mCurrentData.set(year, month, dayOfMonth)
//            }

            mBinding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                mCurrentData.set(
                    year,
                    monthOfYear,
                    dayOfMonth
                )
            }

            mBinding.tvComplete.onClick {
                val timeStamp =
                    SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(mCurrentData.time)
                Logger.i("当前选择时间：${timeStamp}")
                mOnDateCall?.invoke(timeStamp)
                dismiss()
            }
            mBinding.tvCancel.onClick {
                dismiss()
            }
        }

        fun setBirthDayDateCall(onDateCall: ((String?) -> Unit)): Builder {
            mOnDateCall = onDateCall
            return this
        }
    }
}