package com.sergiomeza.lyrics.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiomeza.lyrics.data.`object`.Artist

class DetailViewModel: ViewModel() {
    val artist = MutableLiveData<Artist>()
}