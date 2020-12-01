package com.sergiomeza.lyrics.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.google.gson.Gson
import com.sergiomeza.lyrics.App
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.databinding.ActivityDetailLyricsBinding
import com.sergiomeza.lyrics.databinding.FragmentHistoryBinding
import com.sergiomeza.lyrics.ui.history.HistoryViewModel
import com.sergiomeza.lyrics.ui.search.LyricsSearchViewModelFactory

class DetailLyricsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLyricsBinding
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val artist = intent.getSerializableExtra("artist") as? Artist
        if (artist != null) {
            viewModel.artist.value = artist
            binding = ActivityDetailLyricsBinding.inflate(layoutInflater)
            binding.viewModel = viewModel
            setContentView(binding.root)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "${artist.name} - ${artist.song}"
        } else {
            Toast.makeText(applicationContext, R.string.search_not_found, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}