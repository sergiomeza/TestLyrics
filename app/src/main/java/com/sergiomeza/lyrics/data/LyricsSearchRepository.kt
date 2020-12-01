package com.sergiomeza.lyrics.data

import androidx.annotation.WorkerThread
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.data.dao.ArtistDao
import kotlinx.coroutines.flow.Flow

class LyricsSearchRepository(private val artistDao: ArtistDao) {
    fun getAllSearches(): List<Artist> = artistDao.getSearches()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(artist: Artist) {
        artistDao.insert(artist)
    }
}
