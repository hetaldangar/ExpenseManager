package com.example.myexpensemanger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.myexpensemanger.R

class DialogPaymentAdapter(var paymentList: ArrayList<String>,var n:(String)-> Unit) :
    RecyclerView.Adapter<DialogPaymentAdapter.MyViewHolder>() {

    var pos = -1


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var categoryName: TextView = itemView.findViewById(R.id_number.txtCategory1)
        var rbCategory: RadioButton = itemView.findViewById(R.id.rbCategory)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dialog_category_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.rbCategory.text = paymentList[position]

        holder.rbCategory.setOnClickListener {
            n.invoke(paymentList[position])
            pos = position
            notifyDataSetChanged()
        }
        holder.rbCategory.isChecked = position == pos  //radio button position set

    }




}