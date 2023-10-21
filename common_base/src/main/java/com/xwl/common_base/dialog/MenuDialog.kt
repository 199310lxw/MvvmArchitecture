package com.xwl.common_base.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.xwl.common_base.adapter.MenuDialogAdapter
import com.xwl.common_base.databinding.LayoutMenuDialogBinding
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ClickUtil
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/21
 * descripe
 */
class MenuDialog {
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {
        private val mBinding: LayoutMenuDialogBinding =
            LayoutMenuDialogBinding.inflate(LayoutInflater.from(activity))
        private var mOnCancelListener: (() -> Unit)? = null
        private var mOnItemSelectListener: ((Int, String) -> Unit)? = null

        private var mData = arrayListOf<String>()

        private val mAdapter by lazy { MenuDialogAdapter() }

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ScreenUtil.getScreenWidth() * 3 / 4)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM
            mAdapter.submitList(mData)
            mBinding.tvMenuCancel.onClick {
                mOnCancelListener?.invoke()
                dismiss()
            }
            mBinding.rvMenuList.adapter = mAdapter

            mAdapter.setOnItemClickListener { _, _, position ->
                if (ClickUtil.isFastClick()) return@setOnItemClickListener
                mAdapter.getItem(position)?.let { mOnItemSelectListener?.invoke(position, it) }
            }
        }

        fun setListData(data: ArrayList<String>): Builder {
            this.mData.addAll(data)
            return this
        }


        fun setCancelListener(onCancelListener: (() -> Unit)): Builder {
            mOnCancelListener = onCancelListener
            return this
        }

        fun setItemSelectListener(onItemSelectListener: ((Int, String) -> Unit)): Builder {
            mOnItemSelectListener = onItemSelectListener
            return this
        }
    }
}