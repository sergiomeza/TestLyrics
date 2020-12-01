package com.sergiomeza.lyrics.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergiomeza.lyrics.data.`object`.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * FROM saved_searches")
    fun getSearches(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: Artist)
}