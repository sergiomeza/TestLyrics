package com.sergiomeza.lyrics.ui.history

import android.util.Log
import androidx.lifecycle.*
import com.sergiomeza.lyrics.data.LyricsSearchRepository
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.ui.history.adapter.HistoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HistoryViewModel(private val repository: LyricsSearchRepository) :
    ViewModel(), CoroutineScope {
    var historyAdapter =  HistoryAdapter(arrayListOf())
    lateinit var observerOnHistorySelected: Observer<Artist>
    var historySelected : Artist? = null
    val historyNavigation = MutableLiveData<Boolean>()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val _allSearches = MutableLiveData<List<Artist>>()

    init {
        observerOnHistorySelected = Observer { value ->
            value.let {
                historySelected = value
                historyNavigation.postValue(true)
            }
        }
        historyAdapter.onHistorySelected.observeForever(observerOnHistorySelected)
        getAllsearches()
    }

    fun getAllsearches(){
        viewModelScope.launch(Dispatchers.IO) {
            val allSearches: List<Artist> = repository.getAllSearches()
            launch(Dispatchers.Default){
                _allSearches.postValue(allSearches)
            }
        }
    }

    fun endNavigation(){
        historyNavigation.value = null
        historySelected = null
    }
}