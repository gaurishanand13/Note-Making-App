package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.notesapp.RecyclerView_Handler.myAdapter
import com.example.notesapp.Repositary.Noterepositary
import com.example.notesapp.RoomDataBase_Helper.Note
import com.example.notesapp.RoomDataBase_Helper.roomDataBase
import com.example.notesapp.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {


    val allNotes = ArrayList<Note>()
    val noteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all_note_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_all_notes->{
                noteViewModel.deleteAllNodes()
                Toast.makeText(this@MainActivity,"Deleted all the notes",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            if(resultCode == Activity.RESULT_OK){
                val note = Note(
                    title = data!!.getStringExtra("title"),
                    descirption = data!!.getStringExtra("description"),
                    priority = data!!.getLongExtra("priority",1))
                noteViewModel.insert(note)
                Toast.makeText(this@MainActivity,"Note saved successfully",Toast.LENGTH_SHORT).show()
            }else{
                //On pressing back , the note will not be saved therefore it will be added here
                Toast.makeText(this@MainActivity,"Note not saved",Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = myAdapter(allNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        noteViewModel.GetAllNotes()?.observe(this, Observer {
            adapter.updateTheList(it as ArrayList<Note>)
        })


        /**
         * Adding some example codes
         */
        noteViewModel.insert(Note(title = "Title 1", descirption =  "Description 1", priority = 1))
        noteViewModel.insert(Note(title = "Title 2", descirption =  "Description 2", priority = 2))
        noteViewModel.insert(Note(title = "Title 3", descirption =  "Description 3", priority = 3))
        noteViewModel.insert(Note(title = "Title 4", descirption =  "Description 4", priority = 4))
    }


}
