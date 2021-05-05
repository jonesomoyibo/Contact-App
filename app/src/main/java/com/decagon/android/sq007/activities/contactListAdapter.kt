package com.decagon.android.sq007.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R

class contactListAdapter(private var context: Context, private var contactList: ArrayList<UserContact>, private val clickListener: onItemClickListener) :
    RecyclerView.Adapter<contactListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contactlayout, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(contactholder: ContactViewHolder, position: Int) {
        val contactObject: UserContact = contactList[position]
        contactholder.apply {
            nameView.text = "${contactObject.firstName} ${contactObject.lastName}"
            imageView.setImageResource(R.drawable.imageicon)
            numberView.text = contactObject.phoneNumber
            sourceTextView.text = contactObject.source
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    // Represents the ViewHolder instance object which contains all the child views in the parent View(CardView)
    inner class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(position, contactList)
            }
        }

        val nameView: TextView = contactView.findViewById(R.id.name)
        val imageView: ImageView = contactView.findViewById(R.id.contactimage)
        val numberView: TextView = contactView.findViewById(R.id.contactnumber)
        val sourceTextView: TextView = contactView.findViewById(R.id.sourcetextview)
    }

    interface onItemClickListener {
        fun onItemClick(position: Int, userContactList: ArrayList<UserContact>)
    }
}
