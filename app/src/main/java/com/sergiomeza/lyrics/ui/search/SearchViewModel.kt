package com.sergiomeza.lyrics.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.data.LyricsSearchRepository
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.ui.history.HistoryViewModel
import com.sergiomeza.lyrics.ui.utils.Network.checkNetworkStateWithActiveNetworkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val repository: LyricsSearchRepository) :
    ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var _artistName = MutableLiveData<String>()
    var _artistSong = MutableLiveData<String>()

    private val _showLoading = MutableLiveData(false)
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _response = MutableLiveData<Artist>(null)
    val response: LiveData<Artist?>
        get() = _response

    private val _error = MutableLiveData("")
    val error: LiveData<String?>
        get() = _error

    fun searchSong(context: Context){
        if (!_artistName.value.isNullOrEmpty() && !_artistSong.value.isNullOrEmpty()){
            if(checkNetworkStateWithActiveNetworkInfo(context)) {
                _showLoading.postValue(true)
                viewModelScope.launch(Dispatchers.IO) {
                    val url =
                        URL("https://api.lyrics.ovh/v1/${_artistName.value}/${_artistSong.value}}")
                    (url.openConnection() as? HttpURLConnection)?.run {
                        try {
                            val inputString = inputStream.bufferedReader().use { it.readText() }
                            val artist = Gson().fromJson(inputString, Artist::class.java)
                            if (!artist.lyrics.isNullOrBlank()) {
                                artist.name = _artistName.value?.trim()
                                artist.song = _artistSong.value?.trim()
                                repository.insert(artist = artist)
                                _response.postValue(artist)
                            } else {
                                _response.postValue(artist)
                            }
                            _showLoading.postValue(false)
                        } catch (e: Exception) {
                            _error.postValue(e.localizedMessage)
                        }
                    }
                }
            }
            else {
                _error.postValue(context.getString(R.string.no_connection))
            }
        } else {
            _error.postValue("fields")
        }
    }

    fun endNavigation(){
        _response.value = null
    }
}

class LyricsSearchViewModelFactory(private val repository: LyricsSearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
