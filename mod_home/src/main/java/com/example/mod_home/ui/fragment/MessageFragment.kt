package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import com.example.mod_home.databinding.FragmentMessageBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.dialog.CustomerDialog
import com.xwl.common_lib.ext.onClick

class MessageFragment : BaseVmVbByLazyFragment<HomeViewModel,FragmentMessageBinding>() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
          mViewBinding.tv.onClick{
              showDialog()
          }
    }

    private fun showDialog() {
        val builder = CustomerDialog.Builder()
        val doalog = builder.setTitleText("提示")
//            .setIsCancelVisible(false)
            .setCancelText("取消")
            .setContentText("这是测试的内容")
            .setConfirmText("好的")
            .build()
        doalog.show(parentFragmentManager,"dialog")
    }

    override fun onLazyLoadData() {

    }
}