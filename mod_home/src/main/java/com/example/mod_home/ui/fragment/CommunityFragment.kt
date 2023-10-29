package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mod_home.adapters.CommunityAdapter
import com.example.mod_home.databinding.FragmentCommunityBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.CommunityBean


class CommunityFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentCommunityBinding>() {
    private lateinit var mAdapter: CommunityAdapter

    companion object {
        fun newInstance() = CommunityFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {

    }

    private fun initRv() {
        mAdapter = CommunityAdapter()
        val iterator =
            DividerItemDecoration(requireActivity(), MaterialDividerItemDecoration.VERTICAL)
        mViewBinding.rv.addItemDecoration(iterator)
        mViewBinding.rv.adapter = mAdapter
    }

    private fun getData() {
        val lists = arrayListOf<CommunityBean>()
        val imgList1 = arrayListOf<String>()
        imgList1.add("https://img2.baidu.com/it/u=1797084095,1710748566&fm=253&fmt=auto&app=120&f=JPEG?w=1202&h=676")
        imgList1.add("https://img0.baidu.com/it/u=633157164,81636648&fm=253&fmt=auto&app=138&f=JPEG?w=799&h=500")
        imgList1.add("https://img2.baidu.com/it/u=3148317083,4207239386&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500")
        imgList1.add("https://img1.baidu.com/it/u=1668501659,3592121767&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500")

        val imgList2 = arrayListOf<String>()
        imgList2.add("https://img0.baidu.com/it/u=3780768826,402346580&fm=253&fmt=auto&app=120&f=JPEG?w=1202&h=676")
        imgList2.add("https://img0.baidu.com/it/u=3780768826,402346580&fm=253&fmt=auto&app=120&f=JPEG?w=1202&h=676")

        val imgList3 = arrayListOf<String>()
        imgList3.add("https://img2.baidu.com/it/u=3042072142,1132553708&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500")

        val bean1 = CommunityBean()
        bean1.title = "风景真美啊"
        bean1.author = "mrliu"
        bean1.imgList = imgList1

        val bean2 = CommunityBean()
        bean2.title = "风景真美啊"
        bean2.author = "mrzhang"
        bean2.imgList = imgList2

        val bean3 = CommunityBean()
        bean3.title = "风景真美啊"
        bean3.author = "mrli"
        bean3.imgList = imgList3

        lists.add(bean3)
        lists.add(bean1)
        lists.add(bean2)

        mAdapter.submitList(lists)
    }


    override fun onLazyLoadData() {
        initRv()
        getData()
    }
}