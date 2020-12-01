package com.sergiomeza.lyrics.ui.history.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.databinding.ListitemHistoryBinding
import com.sergiomeza.lyrics.ui.history.HistoryViewModel
import com.sergiomeza.lyrics.ui.history.viewholder.HistoryViewHolder

class HistoryAdapter(var historyList : List<Artist>?) : RecyclerView.Adapter<HistoryViewHolder>() {
    lateinit var context: Context
    var onHistorySelected = MutableLiveData<Artist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        context = parent.context
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ListitemHistoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.listitem_history, parent, false)
        return HistoryViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return historyList?.size ?: 0
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.setMovementItem(historyList?.get(position))
        holder.itemView.setOnClickListener {
            onHistorySelected.value = historyList?.get(position)
        }
    }
}