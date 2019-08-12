package com.example.notesapp.Repositary

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomOpenHelper
import com.example.notesapp.RoomDataBase_Helper.Note
import com.example.notesapp.RoomDataBase_Helper.NoteDao
import com.example.notesapp.RoomDataBase_Helper.roomDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * It handles all the data changes we want to make in our room database or on online server like firebase.
 * this class will also provide the data to the View model class. Though we could have performed all these task in the view model class itself. But it is a good practice
 * to do it in the repositary class .
 * <----------------------------------------->
 * Therefore main task of repo class is to make changes in the data in roomDatabase or onlineApI/FirebaseDatabase.
 * Second task is to provide the data to the view model class which has all the data of the app
 */
class Noterepositary (context: Context): CoroutineScope {

    private var dao : NoteDao? = null
    val allNotes = MutableLiveData<List<Note>>()
//    var mediatorLiveData : MediatorLiveData<List<Note>> = MediatorLiveData()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default   //Default dispatcher as we know is used to perform operations for saving large data permanently on the device


    init {
        var db = Room.databaseBuilder(context,roomDataBase::class.java,"notes.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = db.noteDao()
        /**
         * Linking this mutable Live data with this dao's Live data i.e when the data of the return type of funtion "getAllTheNodes_InProperOrderOf_Priority()" changes.
         * This will surely will be executed
         */
        dao?.getAllTheNodes_InProperOrderOf_Priority()?.observe(context as AppCompatActivity, Observer {

            Log.i("tag of it = ",it.toString())
            allNotes.value = it
            Log.i("tag of allNotes = ",allNotes.toString())
        })
    }



    /**
     * Therefore making this Whenever we make any changes in the allNotes variable. The observer on it will be executed
     */
    fun getAllTheNotes() : MutableLiveData<List<Note>>?{
        return allNotes
    }


    /**
     * We can perform the operations on seperate thread using this way.
     * Here we have set the dispatcher as DEFAULT which is used to save data for a large data on the permanent storage
     */
    fun insert(note: Note){
        launch {
            dao?.insert(note)
        }
    }



    fun update(note: Note){
        launch {
            dao?.update(note)
        }
    }



    fun delete(note: Note){
        launch {
            dao?.delete(note)
        }
    }



    fun DeleteAllTheNotes(){
        launch {
            dao?.deleteAllNotes()
        }
    }

}