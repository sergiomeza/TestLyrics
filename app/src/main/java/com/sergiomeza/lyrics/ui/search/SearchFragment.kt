package com.sergiomeza.lyrics.ui.search

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sergiomeza.lyrics.App
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.databinding.FragmentSearchBinding
import com.sergiomeza.lyrics.ui.MainActivity

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels {
        LyricsSearchViewModelFactory((activity?.application as App).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.viewModel = searchViewModel
        binding.apply {
            buttonSearch.setOnClickListener {
                searchViewModel.searchSong(requireContext())
            }
        }

        searchViewModel.response.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                if (it.lyrics.isNullOrEmpty()){
                    Toast.makeText(context, getString(R.string.search_not_found, searchViewModel._artistName.value,
                        searchViewModel._artistSong.value), Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(
                        SearchFragmentDirections.actionNavigationSearchToDetailLyricsActivity(
                            it
                        )
                    )
                    searchViewModel.endNavigation()
                }
            }
        })

        searchViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it != "fields") {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        })

        searchViewModel.showLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                (activity as MainActivity).showLoading()
            } else {
                (activity as MainActivity).hideLoading()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }
}