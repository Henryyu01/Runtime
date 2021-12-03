package com.example.runtime.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val TAB_NUM = 3

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TAB_NUM

    override fun createFragment(position: Int): Fragment {
        // Return a new fragment instance
        return when (position) {
            0 -> StartFragment()
            1 -> PlayerFragment()
            else -> {
                HistoryFragment()
            }
        }
    }
}