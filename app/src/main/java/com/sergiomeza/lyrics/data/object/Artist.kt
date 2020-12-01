package com.sergiomeza.lyrics.data.`object`

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "saved_searches")
data class Artist(@PrimaryKey(autoGenerate = true) val id: Int,
                  @ColumnInfo(name = "name") var name: String? = "",
                  @ColumnInfo(name = "song") var song: String? = "",
                  @ColumnInfo(name = "lyrics") var lyrics:String?) : Serializable