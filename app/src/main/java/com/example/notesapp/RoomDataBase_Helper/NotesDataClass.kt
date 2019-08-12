package com.example.notesapp.RoomDataBase_Helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteDataTable")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var title : String,
    var descirption: String,
    var priority: Long
)