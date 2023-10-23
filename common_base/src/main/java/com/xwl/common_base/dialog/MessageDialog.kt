package com.xwl.common_base.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.xwl.common_base.databinding.LayoutMessageDialogBinding
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/19
 * descripe
 */
object MessageDialog {
    class Builder(activity: FragmentActivity) :
        BaseDialogFragment.Builder<Builder>(activity) {
        private val mBinding: LayoutMessageDialogBinding =
            LayoutMessageDialogBinding.inflate(LayoutInflater.from(activity))

        private var mOnCancelClick: (() -> Unit)? = null
        private var mOnConfirmClick: (() -> Unit)? = null

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setCancelable(true)
            setWidth(ScreenUtil.getScreenWidth() * 3 / 4)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.SCALE)
            gravity = Gravity.CENTER

            mBinding.tvCancel.onClick {
                dismiss()
                mOnCancelClick?.invoke()
            }
            mBinding.tvConfirm.onClick {
                mOnConfirmClick?.invoke()
                dismiss()
            }
        }

        fun setContent(content: String): Builder {
            mBinding.tvContent.text = content
            return this
        }

        fun setCancelText(cancelText: String): Builder {
            mBinding.tvCancel.text = cancelText
            return this
        }

        fun setConfirmText(confirmText: String): Builder {
            mBinding.tvConfirm.text = confirmText
            return this
        }

        fun setOnCancelClickListener(OnCancelClick: (() -> Unit)): Builder {
            this.mOnCancelClick = OnCancelClick
            return this
        }

        fun setOnConfirmClickListener(OnConfirmClick: (() -> Unit)): Builder {
            this.mOnConfirmClick = OnConfirmClick
            return this
        }
    }
}