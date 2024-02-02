package com.example.myexpensemanger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myexpensemanger.R
import com.example.myexpensemanger.modelclass.CategoryModelClass

class AddCategoryAdapter() :
    RecyclerView.Adapter<AddCategoryAdapter.MyViewHolder>() {
    var list = ArrayList<CategoryModelClass>()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView = itemView.findViewById(R.id.txtCategory)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.categoryName.text = list[position].categoryName
    }

    fun updateData(list: java.util.ArrayList<CategoryModelClass>) {
        this.list=list
        notifyDataSetChanged()
    }
}