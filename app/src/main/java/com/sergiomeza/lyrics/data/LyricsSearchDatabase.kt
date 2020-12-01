package com.sergiomeza.lyrics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sergiomeza.lyrics.data.`object`.Artist
import com.sergiomeza.lyrics.data.dao.ArtistDao


@Database(entities = [Artist::class], version = 1, exportSchema = false)
public abstract class LyricsSearchDatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var INSTANCE: LyricsSearchDatabase? = null

        fun getDatabase(context: Context): LyricsSearchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LyricsSearchDatabase::class.java,
                    "lyrics_search_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
