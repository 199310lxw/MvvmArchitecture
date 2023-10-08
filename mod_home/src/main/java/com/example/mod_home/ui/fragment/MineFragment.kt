package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import com.example.mod_home.databinding.FragmentMineBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment

class MineFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mViewBinding.dampView.setTransverse(false)
//        mViewBinding.tv.onClick{
//            LoginServiceProvider.skipLoginActivity(mContext)
//        }
    }

    override fun onLazyLoadData() {

    }
}