package com.decagon.android.sq007.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.decagon.android.sq007.R


class MySpinnerAdapter(
    val context: Context,
    val imageIds: IntArray,
    val locationNames: Array<String>
): BaseAdapter() {
    override fun getCount(): Int {
    return imageIds.size
    }

    override fun getItem(position: Int): Any? {
          return null
    }

    override fun getItemId(position: Int): Long {
      return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var spinnerView=view
        spinnerView= LayoutInflater.from(context).inflate(R.layout.spinner_item_layout, null)
        spinnerView.setTag(spinnerView.id,"${spinnerView.id}$position")

        val icon: ImageView = spinnerView!!.findViewById(R.id.locationicon) as ImageView
        val names = spinnerView!!.findViewById(R.id.contact_location) as TextView
        icon.setImageResource(imageIds[position]);
        names.setText(locationNames[position]);
        return spinnerView
    }

}