package com.xwl.common_base.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.databinding.DialogAgreementBinding
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/25
 * descripe
 */
object AgreeMentDialog {
    class Builder(activity: FragmentActivity) :
        BaseDialogFragment.Builder<ChooseSexDialog.Builder>(activity) {
        private val mBinding: DialogAgreementBinding =
            DialogAgreementBinding.inflate(LayoutInflater.from(activity))

        private var mOnAgreeListener: (() -> Unit)? = null
        private var mOnDisAgreeListener: (() -> Unit)? = null

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setWidth(ScreenUtil.getScreenWidth() * 3 / 4)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.SCALE)
            gravity = Gravity.CENTER
            mBinding.tvOne.onClick {
                WebViewActivity.start(context, UrlConstants.AGREENMENT_URL, "用户协议")
            }
            mBinding.tvTwo.onClick {
                WebViewActivity.start(context, UrlConstants.AGREENMENT_URL, "隐私政策")
            }
            mBinding.btnAgree.onClick {
                mOnAgreeListener?.invoke()
                dismiss()
            }
            mBinding.btnDisAgree.onClick {
                mOnDisAgreeListener?.invoke()
                dismiss()
            }
        }

        fun setAgreeClickListener(onAgreeListener: () -> Unit): Builder {
            this.mOnAgreeListener = onAgreeListener
            return this
        }

        fun setDisAgreeClickListener(onDisAgreeListener: () -> Unit): Builder {
            this.mOnDisAgreeListener = onDisAgreeListener
            return this
        }
    }


}