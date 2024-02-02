package com.example.myexpensemanger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.myexpensemanger.R
import com.example.myexpensemanger.modelclass.CategoryModelClass

class DialogCategoryAdapter(var list: ArrayList<CategoryModelClass>, var n: (String) -> Unit) :
    RecyclerView.Adapter<DialogCategoryAdapter.MyViewHolder>() {

    var pos = -1


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rbCategory: RadioButton = itemView.findViewById(R.id.rbCategory)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dialog_category_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.rbCategory.text = list[position].categoryName

        holder.rbCategory.setOnClickListener {
            n.invoke(list[position].categoryName)
            pos = position
            notifyDataSetChanged()
        }

        holder.rbCategory.isChecked = position == pos  //radio button position set

//        holder.layDialog.setOnClickListener {
//
//        }

    }


}