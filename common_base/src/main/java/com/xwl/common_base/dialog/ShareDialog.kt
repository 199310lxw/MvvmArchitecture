package com.xwl.common_base.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.xwl.common_base.R
import com.xwl.common_base.adapter.ShareDialogAdapter
import com.xwl.common_base.databinding.LayoutShareDialogBinding
import com.xwl.common_lib.bean.ShareBean
import com.xwl.common_lib.utils.ClickUtil

/**
 * @author lxw
 * @date   2023/4/25 14:35
 * @desc   性别选择弹框
 */
class ShareDialog {
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {
        private val mBinding: LayoutShareDialogBinding =
            LayoutShareDialogBinding.inflate(LayoutInflater.from(activity))
        private lateinit var mAdapter: ShareDialogAdapter
        private var mOnItemClickListener: ((Int, String) -> Unit)? = null

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM
            initRv()
        }

        fun setOnItemClickListener(OnItemClickListener: (Int, String) -> Unit): Builder {
            this.mOnItemClickListener = OnItemClickListener
            return this
        }

        private fun initRv() {
            val data: MutableList<ShareBean> = arrayListOf()
            activity.getDrawable(R.drawable.share_wechat_ic)?.let {
                ShareBean(
                    it,
                    getString(R.string.share_platform_wechat)
                )
            }?.let {
                data.add(
                    it
                )
            }
            activity.getDrawable(R.drawable.share_moment_ic)?.let {
                ShareBean(
                    it,
                    getString(R.string.share_platform_moment)
                )
            }?.let {
                data.add(
                    it
                )
            }
            activity.getDrawable(R.drawable.share_qq_ic)?.let {
                ShareBean(
                    it,
                    getString(R.string.share_platform_qq)
                )
            }?.let {
                data.add(
                    it
                )
            }
            activity.getDrawable(R.drawable.share_qzone_ic)?.let {
                ShareBean(
                    it,
                    getString(R.string.share_platform_qzone)
                )
            }?.let {
                data.add(
                    it
                )
            }
            activity.getDrawable(R.drawable.share_link_ic)?.let {
                ShareBean(
                    it,
                    getString(R.string.share_platform_link)
                )
            }?.let {
                data.add(
                    it
                )
            }
            mAdapter = ShareDialogAdapter()
            mAdapter.submitList(data)
            mBinding.rvShareList.layoutManager = GridLayoutManager(activity, data.size)
            mBinding.rvShareList.adapter = mAdapter
            mAdapter.setOnItemClickListener { _, _, position ->
                if (ClickUtil.isFastClick()) return@setOnItemClickListener
                mOnItemClickListener?.invoke(position, data[position].shareName)
                dismiss()
            }
        }
    }


}