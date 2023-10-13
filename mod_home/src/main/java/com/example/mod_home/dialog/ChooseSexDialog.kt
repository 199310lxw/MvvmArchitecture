package com.sum.user.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.mod_home.R
import com.example.mod_home.databinding.DialogSexChooseBinding
import com.xwl.common_base.dialog.BaseDialog
import com.xwl.common_base.dialog.BaseDialogFragment
import com.xwl.common_lib.ext.onClick

/**
 * @author mingyan.su
 * @date   2023/4/25 14:35
 * @desc   性别选择弹框
 */
class ChooseSexDialog {
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {
        private var mOnSexChooseCall: ((String) -> Unit)? = null

        private val mBinding: DialogSexChooseBinding =
            DialogSexChooseBinding.inflate(LayoutInflater.from(activity))

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM

            mBinding.clSexBoy.onClick {
                mOnSexChooseCall?.invoke(resources.getString(R.string.user_sex_boy))
                dismiss()
            }
            mBinding.clSexGirl.onClick {
                mOnSexChooseCall?.invoke(resources.getString(R.string.user_sex_girl))
                dismiss()
            }
            mBinding.tvCancel.onClick {
                dismiss()
            }
        }

        fun setOnSexChooseCall(onSexChooseCall: ((String) -> Unit)): Builder {
            mOnSexChooseCall = onSexChooseCall
            return this
        }
    }
}