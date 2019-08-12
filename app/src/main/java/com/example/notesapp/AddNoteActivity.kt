package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saveNote){
            saveNote()
            return true
        }else{
            return super.onOptionsItemSelected(item)
        }
    }

    fun saveNote(){
        val title = TitleEditText.text.toString()
        val description =  descrptionEditText.text.toString()
        val priority = number_picker.value

        if(title.equals("") || description.equals("")){
            Toast.makeText(this@AddNoteActivity,"Please add the title and description",Toast.LENGTH_SHORT).show()
        }else{
            val resultIntent = Intent()
            intent.putExtra("title",title)
            intent.putExtra("description",description)
            intent.putExtra("priority",priority)

            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        /**
         * Setting min and max value of number picker
         */
        number_picker.minValue = 1
        number_picker.maxValue = 20

        /**
         * Setting the icon for back as a cross
         */
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.setTitle("Add Note")
    }

}
