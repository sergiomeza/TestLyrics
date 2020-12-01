package com.sergiomeza.lyrics.ui.history.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.databinding.ListitemHistoryBinding

class HistoryViewHolder(binding: ListitemHistoryBinding) : RecyclerView.ViewHolder(binding.root){
    private var binding: ListitemHistoryBinding? = null

    init {
        this.binding = binding
    }

    fun setMovementItem(artist: Artist?){
        binding?.artist = artist
        binding?.executePendingBindings()
    }
}
