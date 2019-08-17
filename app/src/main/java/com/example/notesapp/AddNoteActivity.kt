package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
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

            /**
             * First save the note
             */
            val title = TitleEditText.text.toString()
            val description =  descrptionEditText.text.toString()
            val priority = number_picker.value

            if(title.equals("") || description.equals("")){
                Toast.makeText(this@AddNoteActivity,"Please add the title and description",Toast.LENGTH_SHORT).show()
            }else{
                val resultIntent = Intent()
                resultIntent.putExtra("title",title)
                resultIntent.putExtra("description",description)
                resultIntent.putExtra("priority",priority)

                /**
                 * Since we need to pass the id , if we are updating the Note, then only the roomDataBase wil be able to update the note
                 */
                if(intent.hasExtra("id")){
                    resultIntent.putExtra("id",intent.getLongExtra("id",1))
                }

                setResult(RESULT_OK,resultIntent)
                finish()
            }
            return true
        }else{
            return false
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

        /**
         * Now we will set the title as "ADD NOTE" if we are adding the note and if come to this activity by clicking an item, then we should display the title as EDIT NOTE
         */
        if(intent.hasExtra("id"))
        {
            supportActionBar?.setTitle("Edit Note")
            /**
             * And if the user is editing the note, then we should also display the note details that was there by default
             */
            //Editable.Factory.getInstance().newEditable(savedString)
            TitleEditText.text = Editable.Factory.getInstance().newEditable(intent.getStringExtra("title"))
            descrptionEditText.text = Editable.Factory.getInstance().newEditable(intent.getStringExtra("description"))
            number_picker.value = intent.getLongExtra("priority",1).toInt()

        }else{
            supportActionBar?.setTitle("Add Note")
        }
    }

}
