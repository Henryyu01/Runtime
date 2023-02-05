package com.henry.runtime.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val TAB_NUM = 3

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return TAB_NUM
    }

    override fun createFragment(position: Int): Fragment {
        // Return a new fragment instance
        return when (position) {
            1 -> StartFragment()
            2 -> PlayerFragment()
            else -> HistoryFragment()
        }
    }

}