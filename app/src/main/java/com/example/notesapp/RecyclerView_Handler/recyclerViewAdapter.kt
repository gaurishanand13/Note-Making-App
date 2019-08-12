package com.example.notesapp.RecyclerView_Handler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.RoomDataBase_Helper.Note
import kotlinx.android.synthetic.main.recyclerview_item_view.view.*


class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)



class myAdapter(var allTheNotes: ArrayList<Note>): RecyclerView.Adapter<viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.recyclerview_item_view, parent, false)
        return viewHolder(itemView)
    }

    override fun getItemCount(): Int  = allTheNotes.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val note = allTheNotes!![position]
        holder.itemView.titleTextView.text = note.title
        holder.itemView.descrptionTextView.text = note.descirption
        holder.itemView.priorityTextView.text = note.priority.toString()
    }

    fun updateTheList(newList_OfNotes: ArrayList<Note>) {
        allTheNotes.clear()
        allTheNotes.addAll(newList_OfNotes)
        notifyDataSetChanged()
    }

}