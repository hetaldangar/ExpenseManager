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
        ArrayList<IncomeExpenseModelClass>()  //create  array list  and set model class

    lateinit var adapter1: TransactionAdapter  //define  adapter class
    lateinit var dbT: SqLiteHelperData   // define  data base class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionBinding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(transactionBinding.root)   //set xml file

        dbT = SqLiteHelperData(this)  // set context class in sqlite
        initView()
        amount()
    }

    private fun amount() {
//        var incomeAmount=transactionBinding.txtIncome.text
//        var expenseAmount=transactionBinding.txtExpense.text
//        incomeAmount= adapter1.incomeFunction().toString()

//        Log.e("tra", "incomeAmount: $incomeAmount")
//        expenseAmount=adapter1.expenseFunction().toString()
//        Log.e("tra", "expenseAmount: $expenseAmount")
    }

    private fun initView() {
        transactionBinding.imgBack.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)    //this activity to MainActivity Move
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
            Log.e("TAG", "sanjay_ID: " + it.id)
            Log.e("TAG", "sanjay_AMOUNT: " + it.amount)

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
        var manger1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        transactionBinding.recycleTransaction.layoutManager = manger1
        transactionBinding.recycleTransaction.adapter = adapter1

        listTransaction = dbT.displayTransact()

        adapter1.updateData(listTransaction)

    }
}