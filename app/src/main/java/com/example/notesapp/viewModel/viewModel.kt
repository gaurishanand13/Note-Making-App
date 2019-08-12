package com.example.notesapp.viewModel

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.notesapp.Repositary.Noterepositary
import com.example.notesapp.RoomDataBase_Helper.Note


/**
 * View Model will provide data of the app to the UI
 * Also it will ask for data from the repositary.
 * And if we want to make any changes in the database. pass that data to the repo class. That repo class will make changes to both RoomDatabase and online Api database
 */

class NoteViewModel(val context: Context) : ViewModel() {

    private var  allNotes : MutableLiveData<List<Note>> = MutableLiveData()
    private var noterepositary : Noterepositary? = null

    /**
     * This function will be basically used to add an observer to this LIVE data whereever this view model is used
     * Now here we have linked the Live data of the repositary with our view model. It will be called automatically when the object of the class is made
     */
    init {
        Log.i("tag","Hey i am in init")
        noterepositary = Noterepositary(context)
        Log.i("tag","Hey i am in init2")
        noterepositary?.getAllTheNotes()?.observe(context as AppCompatActivity, Observer {
            allNotes?.value = it
        })
        Log.i("tag","Hey i am in init3")
    }


    fun GetAllNotes() : LiveData<List<Note>>?{
        return allNotes
    }


    fun insert(note: Note){
        noterepositary?.insert(note)
    }



    fun update(note: Note){
        noterepositary?.update(note)
    }


    fun delete(note: Note){
        noterepositary?.delete(note)
    }


    fun deleteAllNodes(){
        noterepositary?.DeleteAllTheNotes()
    }

}
