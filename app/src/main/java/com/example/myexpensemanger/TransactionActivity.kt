package com.example.myexpensemanger.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpensemanger.adapter.TransactionAdapter
import com.example.myexpensemanger.databinding.ActivityTransactionBinding
import com.example.myexpensemanger.databinding.DialogDeleteBinding
import com.example.myexpensemanger.modelclass.IncomeExpenseModelClass
import com.example.myexpensemanger.sqlite.SqLiteHelperData

class TransactionActivity : AppCompatActivity() {

    lateinit var transactionBinding: ActivityTransactionBinding  //activity biding

    var listTransaction =
        ArrayList<IncomeExpenseModelClass>()
  //  var dbHelper = SqLiteHelperData(this)

    lateinit var adapter1: TransactionAdapter
    lateinit var dbT: SqLiteHelperData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionBinding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(transactionBinding.root)

   // var dbHelper = SqLiteHelperData(this)
       // dbHelper.fetchTotalAmounts()

        dbT = SqLiteHelperData(this)
        initView()
        amount()

    }



    private fun amount() {
        var totalIncome = 0.0
        var totalExpense = 0.0

        // Iterate through the list of transactions to calculate the total income and expense
        for (transaction in listTransaction) {
            val amount = transaction.amount.toDoubleOrNull() ?: 0.0// Convert to Double or default to 0.0 if conversion fails

            if (transaction.page == "Income") {
                totalIncome += amount
            } else if (transaction.page == "Expense") {
                totalExpense += amount
            }
        }

        // Set the calculated values to the corresponding TextViews
        transactionBinding.txtIncome.text = totalIncome.toString()
        transactionBinding.txtExpense.text = totalExpense.toString()

        Log.e("tra", "totalIncome: $totalIncome")
        Log.e("tra", "totalExpense: $totalExpense")

        val txtTotal = totalIncome - totalExpense // Assuming you want to store the difference between income and expense
        transactionBinding.txtTotal.text = txtTotal.toString()

        // Now you can use txtTotal as needed
        Log.e("tra", "txtTotal: $txtTotal")
    }







    private fun initView() {
        transactionBinding.imgBack.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        listTransaction = dbT.displayTransact()   //show data base in this class

        adapter1 = TransactionAdapter(this, listTransaction, {  // invoke methode in set data

            var title_update = "Update Data"  // define title text
            var iconeName =
                "Update"        // define update text and  addScreen activity in change done text to  update text
            var i = Intent(this, AddScreenActivty::class.java)
            i.putExtra("id_no", it.id)  //id use for data update
            i.putExtra("title", title_update)  //set title
            i.putExtra("amount", it.amount)
            i.putExtra("note", it.note)
            i.putExtra("page", it.page)
            i.putExtra("iconName", iconeName)
            i.putExtra("updateRecord", true)   //data update key pass in addScreen Activity
            startActivity(i)
            Log.e("TAG", "ID: " + it.id)
            Log.e("TAG", "AMOUNT: " + it.amount)

        }, { id ->                //use invoke for  delete record


            val dialog = Dialog(this)
            val dialogBinding = DialogDeleteBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSet.setOnClickListener {

                dbT.deleteData(id)

                Toast.makeText(this, "delete record success", Toast.LENGTH_SHORT).show()
                listTransaction = dbT.displayTransact()
                adapter1.updateData(listTransaction)

                dialog.dismiss()
            }
            dialogBinding.btnCancel.setOnClickListener {

                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()

        })
        var manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        transactionBinding.recycleTransaction.layoutManager = manger
        transactionBinding.recycleTransaction.adapter = adapter1

        listTransaction = dbT.displayTransact()

        adapter1.updateData(listTransaction)

    }
}
