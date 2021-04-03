package com.ma.saper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class MyRecyclerViewAdapter internal constructor(
    context: Context,
    private val data: MutableList<Int>
) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var listener: ItemClickListener? = null
    private val resources = context.resources

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            // empty unpressed
            0 -> {
                holder.button.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                holder.button.text = ""
            }
            // bomb unpressed
            10 -> {
                holder.button.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                holder.button.text = "T"
            }
            // bomb pressed
            20 -> {
                holder.button.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                holder.button.text = ""
            }
            // pressed number of near by elements
            1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                holder.button.setBackgroundColor(resources.getColor(android.R.color.white))
                holder.button.text = "$item"
            }
            // empty pressed
            -1 -> {
                holder.button.setBackgroundColor(resources.getColor(R.color.white))
                holder.button.text = ""
            }
        }

    }

    // total number of cells
    override fun getItemCount(): Int {
        return data.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById<Button>(R.id.cell)
            .apply {
                setOnClickListener { listener?.onItemClick(it, adapterPosition) }
            }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return data[id].toString()
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}