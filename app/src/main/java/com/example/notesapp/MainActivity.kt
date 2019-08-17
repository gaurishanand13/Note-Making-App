package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.notesapp.RecyclerView_Handler.myAdapter
import com.example.notesapp.RecyclerView_Handler.onClicInterface_ForItems
import com.example.notesapp.Repositary.Noterepositary
import com.example.notesapp.RoomDataBase_Helper.Note
import com.example.notesapp.RoomDataBase_Helper.roomDataBase
import com.example.notesapp.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
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
        if(resultCode == RESULT_OK){

            val title = data!!.getStringExtra("title")
            val descirption = data.getStringExtra("description")
            val priority = data.getIntExtra("priority",1).toLong()
            val id = data.getLongExtra("id",1)
            if(requestCode == 123){
                val note = Note(
                    title = title,
                    descirption = descirption,
                    priority = priority
                )
                noteViewModel.insert(note)
                Toast.makeText(this@MainActivity,"Note added",Toast.LENGTH_SHORT).show()
            }
            if(requestCode == 124){
                var note = Note(
                    title = title,
                    descirption = descirption,
                    priority = priority
                )
                note.id = id
                noteViewModel.update(note)
                Toast.makeText(this@MainActivity,"Note updated",Toast.LENGTH_SHORT).show()

            }
        }else if(requestCode == 123){
            //On pressing back , the note will not be saved therefore it will not be added here
            Toast.makeText(this@MainActivity,"Note not added",Toast.LENGTH_SHORT).show()
        }else {
            //On pressing back , the note will not be saved therefore it will not be updated here
            Toast.makeText(this@MainActivity,"Note not updated",Toast.LENGTH_SHORT).show()
        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = myAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        noteViewModel.GetAllNotes()?.observe(this, Observer {
            adapter.submitList(it as ArrayList<Note>)
        })


        /**
         * Adding one example codes
         */
        noteViewModel.insert(Note(title = "Title 1", descirption =  "Description 1", priority = 1))


        /**
         * On Click Listener on the floating action button
         */
        floatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity,AddNoteActivity::class.java),123)
        }


        /**
         * Now we will add the functionality of that when we swipe the node get deleted
         */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val note_to_be_delted = adapter.getNote(viewHolder.adapterPosition)
                noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))

                /**
                 * SnackBar to show if the user wants to undo the process of delete
                 */
                Snackbar.make(coordinatorLayout,"Deleted it by mistake",Snackbar.LENGTH_LONG)
                    .setAction("UNDO IT",object : View.OnClickListener{
                        override fun onClick(p0: View?) {
                            /**
                             * Therefore it user clicks undo it, we should add again the deleted note
                             */
                            noteViewModel.insert(note_to_be_delted)
                        }
                    }).show()
            }

        }).attachToRecyclerView(recyclerView)


        /**
         * Setting up the interface for the onClick on the recyler view
         */
        adapter.myInterface = object : onClicInterface_ForItems{

            override fun clickOnItems(position: Int) {
                val intent = Intent(this@MainActivity,AddNoteActivity::class.java)
                intent.putExtra("title",allNotes.get(position).title)
                intent.putExtra("description",allNotes.get(position).descirption)
                intent.putExtra("id",allNotes.get(position).id)
                intent.putExtra("priority",allNotes.get(position).priority)
                startActivityForResult(intent,124)
            }

        }
    }


}
