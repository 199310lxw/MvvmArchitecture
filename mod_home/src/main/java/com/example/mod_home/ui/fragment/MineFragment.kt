package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.databinding.FragmentMineBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.LoginServiceProvider

class MineFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mViewBinding.tv.onClick{
            LoginServiceProvider.skipLoginActivity(mContext)
        }
    }

    override fun onLazyLoadData() {

    }
}