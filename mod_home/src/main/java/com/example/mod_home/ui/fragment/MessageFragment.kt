package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentMessageBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment


class MessageFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMessageBinding>() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        init()
    }

    private fun init() {
        mViewBinding.tv.text = "红红火火恍恍惚惚或或或或或或或或"
        val mList: MutableList<Int> = ArrayList()
        mList.add(R.drawable.test1)
        mList.add(R.drawable.test2)
        mList.add(R.drawable.test3)
        mList.add(R.drawable.test4)
        mViewBinding.imgContainer.setImagesData(mList)

    }


    override fun onLazyLoadData() {

    }
}