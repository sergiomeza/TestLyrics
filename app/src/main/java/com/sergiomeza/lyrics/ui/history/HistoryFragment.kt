package com.sergiomeza.lyrics.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sergiomeza.lyrics.App
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.databinding.FragmentHistoryBinding
import com.sergiomeza.lyrics.ui.detail.DetailViewModel
import com.sergiomeza.lyrics.ui.search.LyricsSearchViewModelFactory
import com.sergiomeza.lyrics.ui.search.SearchViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels {
        LyricsSearchViewModelFactory((activity?.application as App).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.viewModel = viewModel
        binding.recyclerViewHistory.adapter = viewModel.historyAdapter

        //Menu Selection
        viewModel.historyNavigation.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewModel.historySelected != null) {
                if (viewModel.historySelected!!.lyrics.isNullOrEmpty()) {
                    Toast.makeText(
                        context, getString(
                            R.string.search_not_found, viewModel.historySelected!!.name,
                            viewModel.historySelected!!.song
                        ), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(
                        HistoryFragmentDirections.actionNavigationHistoryToDetailLyricsActivity(
                            viewModel.historySelected!!
                        )
                    )
                    viewModel.endNavigation()
                }
            }
        })

        viewModel._allSearches.observe(viewLifecycleOwner, Observer {
            viewModel.historyAdapter.historyList = it
            viewModel.historyAdapter.notifyDataSetChanged()
        })

        binding.lifecycleOwner = this
        return binding.root
    }
}