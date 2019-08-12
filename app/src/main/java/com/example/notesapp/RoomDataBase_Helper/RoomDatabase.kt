package com.example.notesapp.RoomDataBase_Helper

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Note if we want to create the room data base once and use it once, then do it in the sir way. but if you will use your databse multile places
 * prefer to create the database here and pass the instance
 */

@Database(entities = arrayOf(Note::class),version = 1)
abstract class roomDataBase : RoomDatabase(){

    abstract fun noteDao() : NoteDao


//    public fun getInstance(context: Context) : roomDataBase?{
//        if(noteDatabase==null){
//            noteDatabase = Room.databaseBuilder(context.applicationContext,roomDataBase::class.java,"notes.db")
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//        return noteDatabase
//    }
}