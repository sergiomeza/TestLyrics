package com.sergiomeza.lyrics

import android.app.Application
import com.sergiomeza.lyrics.data.LyricsSearchDatabase
import com.sergiomeza.lyrics.data.LyricsSearchRepository

class App : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { LyricsSearchDatabase.getDatabase(this) }
    val repository by lazy { LyricsSearchRepository(database.artistDao()) }
}
