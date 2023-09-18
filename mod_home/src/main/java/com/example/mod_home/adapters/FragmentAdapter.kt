package com.example.mod_home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * author: Administrator
 * time: 2022/8/15
 * desc: Pager2AdapterFragment
 */
open class FragmentAdapter(private val list: List<Fragment>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}
