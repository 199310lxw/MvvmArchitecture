package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mod_home.adapters.CommunityAdapter
import com.example.mod_home.databinding.FragmentCommunityBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.xwl.common_base.activity.WebViewActivity
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
        mAdapter.setOnItemClickListener { adapter, _, position ->
            adapter.getItem(position)
                ?.let { WebViewActivity.start(requireActivity(), it.url, "") }
        }
    }

    private fun getData() {
        val lists = arrayListOf<CommunityBean>()
        val imgList1 = arrayListOf<String>()
        imgList1.add("https://www.runoob.com/wp-content/uploads/2018/09/1538030297-3401-20150904094424185-2018280216.gif")
        imgList1.add("https://www.runoob.com/wp-content/uploads/2018/09/1538030296-7490-20150904094019903-1923900106.jpg")
        imgList1.add("https://www.runoob.com/wp-content/uploads/2018/09/1538030296-8668-20150904095142060-1017190812.gif")
        imgList1.add("https://www.runoob.com/wp-content/uploads/2018/09/1538030297-3779-20150904110054856-961661137.png")

        val imgList2 = arrayListOf<String>()
        imgList2.add("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2016/11/29/dee6212d0fd52e02bb905f34046fb5d8.jpg~tplv-t2oaga2asx-jj-mark:3024:0:0:0:q75.awebp")
        imgList2.add("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2016/11/29/8239b10056a01f025082e594c40d3bfa.jpg~tplv-t2oaga2asx-jj-mark:3024:0:0:0:q75.awebp")

        val imgList3 = arrayListOf<String>()
        imgList3.add("https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1cbc1a7ca5fa4cb5971424c7b77d4f02~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?")

        val bean1 = CommunityBean()
        bean1.title = "计算机网络基础知识总结"
        bean1.author = "mrliu"
        bean1.url = "https://www.runoob.com/w3cnote/summary-of-network.html"
        bean1.imgList = imgList1

        val bean2 = CommunityBean()
        bean2.title = "会了这些，你也会成为自定义view的大神减少对业务层代码的入侵"
        bean2.author = "mrzhang"
        bean2.url = "https://juejin.cn/post/6844903453412573191"
        bean2.imgList = imgList2

        val bean3 = CommunityBean()
        bean3.title =
            "常用到的几个Kotlin开发技巧，减少对业务层代码的入侵"
        bean3.author = "mrli"
        bean3.url = "https://juejin.cn/post/7148823027608715295"
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