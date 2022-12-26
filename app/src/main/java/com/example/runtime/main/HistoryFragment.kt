package com.example.runtime.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.runtime.HistoryViewModel
import com.example.runtime.repositories.HistoryRepository

class HistoryFragment : Fragment() {

    private val historyRepository = HistoryRepository()
    private val historyViewModel: HistoryViewModel by viewModels()



}