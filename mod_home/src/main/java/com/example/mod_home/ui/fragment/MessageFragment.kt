package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.databinding.FragmentMessageBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment

class MessageFragment : BaseVmVbByLazyFragment<HomeViewModel,FragmentMessageBinding>() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {

    }

    override fun onLazyLoadData() {

    }
}