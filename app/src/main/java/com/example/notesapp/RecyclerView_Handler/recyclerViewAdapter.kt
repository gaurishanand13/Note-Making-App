package com.example.notesapp.RecyclerView_Handler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.RoomDataBase_Helper.Note
import kotlinx.android.synthetic.main.recyclerview_item_view.view.*


class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

interface onClicInterface_ForItems{
    fun clickOnItems(position: Int)
}



/**
 * Note while using notifyDataSetChanged in recycler view which deletes and update the whole list. This process can be bit lengthy as when the list is very
 * large like 1000 or more enteries, then it will will make the update of the list very slow. Therefore we will use "diffutil"
 * DiffUtil is a utility class which is basically an alternative for notifyDataSetChanged in the recycler View. Since notifyDataSetChanged is costly. Whenever invoked it
 * updates each and every RecyclerView row. Whereas DiffUtil does not call each and every row. Instead, it updates only those rows that are different between the Lists of values.
 * It will handle it on its own. As it has internally the following methods which works on their own and makes data updation fast - notifyItemMoved() , notifyItemRangeChanged() ,
 * notifyItemRangeInserted() , notifyItemRangeRemoved()
 */

/**
 * Therefore this class will handle all the comparison between two lists. This utility class for recycler view is made mainly to compare two list
 */
class DiffCallBack : DiffUtil.ItemCallback<Note>(){

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        /**
         * This method is called only when the items are are same
         */
        return (oldItem.descirption == newItem.descirption) && (oldItem.title == newItem.title) && (oldItem.priority == newItem.priority)
    }


}

/**
 * This List adapter is not the same as simple base adapter class. It has been integrated with the recycler view class
 */
class myAdapter: ListAdapter<Note , viewHolder>(DiffCallBack()) {

    var myInterface : onClicInterface_ForItems? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.recyclerview_item_view, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val note = getItem(position)
        holder.itemView.titleTextView.text = note.title
        holder.itemView.descrptionTextView.text = note.descirption
        holder.itemView.priorityTextView.text = note.priority.toString()

        /**
         * Setting up the onClick for each item
         */
        holder.itemView.setOnClickListener {
            myInterface?.clickOnItems(position)
        }
    }

    fun getNote(position: Int) = getItem(position)
}