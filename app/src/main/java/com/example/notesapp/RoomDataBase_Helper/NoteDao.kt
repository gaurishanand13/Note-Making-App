package com.example.notesapp.RoomDataBase_Helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

/**
 * Since we are making a large database. Therefore to fetch data from
 */


@Dao
interface NoteDao{

    @Insert
    suspend fun insert(note : Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM NoteDataTable")
    suspend fun deleteAllNotes()

    /**
     *     Therefore whenever the change in the return type of the this function takes place, the code in the observer for this LiveData will be executed
     *     i.e any changes in the table made be it, add or delete or update or anything. The observer will handle it.
     */

    @Query("SELECT * FROM NoteDataTable ORDER BY priority DESC")
    fun getAllTheNodes_InProperOrderOf_Priority() : LiveData<List<Note>>
}