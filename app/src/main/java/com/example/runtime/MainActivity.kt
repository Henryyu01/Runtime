package com.example.runtime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.runtime.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val LOG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    // Tab layout + view pager
    private var tabNames = arrayOf("Run", "Play", "Sessions")
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.pager
        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) {
            tab, position -> tab.text = tabNames[position]
        }
    }
}