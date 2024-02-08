package com.example.myexpensemanger.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpensemanger.adapter.DialogCategoryAdapter
import com.example.myexpensemanger.adapter.DialogPaymentAdapter
import com.example.myexpensemanger.databinding.ActivityAddScreenActivtyBinding
import com.example.myexpensemanger.databinding.DialogCategoryBinding
import com.example.myexpensemanger.databinding.DialogPaymentBinding
import com.example.myexpensemanger.sqlite.SqLiteHelperData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddScreenActivty : AppCompatActivity() {


    private lateinit var addScreenBinding: ActivityAddScreenActivtyBinding
    private lateinit var dbS: SqLiteHelperData
    private lateinit var selectedCategory: String
    private lateinit var selectedMode: String
    private lateinit var selectedDateValue: String
    private lateinit var page: String
    private var id_number: Int = 0
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addScreenBinding = ActivityAddScreenActivtyBinding.inflate(layoutInflater)
        setContentView(addScreenBinding.root)
        dbS = SqLiteHelperData(this)

        if (intent != null && intent.hasExtra("updateRecord")) {
            flag = 1
            id_number = intent.getIntExtra("id_no", 0)

        }

        initView()
    }

    private fun initView() {
        categoryDialogData()
        dataAndTime()
        paymentMode()
        page = intent.getStringExtra("Page").toString()
        when (page) {  // variable set in when statement

            "income" -> {
                addScreenBinding.rbIncome.isChecked = true
                addScreenBinding.txtTitle.text = "Add Income"

            }

            "expense" -> {
                addScreenBinding.rbExpense.isChecked = true
                addScreenBinding.txtTitle.text = "Add Expense"
            }

        }


//        var data = intent.getIntExtra("Type",0)
//
//        if (data == 0) {
//            addScreenBinding.rbIncome.isChecked = true
//            addScreenBinding.txtTitle.text = "Add Income"
//        } else if (data == 1) {
//            addScreenBinding.rbExpense.isChecked = true
//            addScreenBinding.txtTitle.text = "Add Expense"
//        }

        addScreenBinding.imgBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }



        addScreenBinding.imgDone.setOnClickListener {
            val amount = addScreenBinding.edtAmount.text.toString()
            val note = addScreenBinding.edtNote.text.toString()

            if (amount.isEmpty() || note.isEmpty()) {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            } else {
                val selectId: Int = addScreenBinding.rgGroup.checkedRadioButtonId
                if (selectId != -1) {
                    val selectedRadioButton: RadioButton = findViewById(selectId)
                    val text = selectedRadioButton.text.toString()
                    page = if (text == "Income") "Income" else "Expense"
                }

                if (flag == 1) {
                    dbS.updateRecord(amount, selectedCategory, selectedDateValue, selectedMode, note, page, id_number)
                } else {
                    dbS.insertTransact(amount, selectedCategory, selectedDateValue, selectedMode, note, page)
                }

                val intent = Intent(this, TransactionActivity::class.java)
                finish()
                startActivity(intent)
                Toast.makeText(this, "Your Data Saved", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun categoryDialogData() {
        selectedCategory = "Default Category"

        addScreenBinding.cdCategory.setOnClickListener {
            val dialog = createCategoryDialog()
            dialog.show()
        }


    }

    private fun createCategoryDialog(): Dialog {
        val dialog = Dialog(this)
        val dialogBinding = DialogCategoryBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val list = dbS.displayCategory()


        val adapter = DialogCategoryAdapter(list) { categoryName ->
            Log.e("TAG", "categoryDialogData: $categoryName")
            selectedCategory = categoryName
          //  addScreenBinding.txtCategorySelected.text = selectedCategory
        }

        dialogBinding.recycleDialog.layoutManager = LinearLayoutManager(this)
        dialogBinding.recycleDialog.adapter = adapter

        dialogBinding.btnSet.setOnClickListener {
            Toast.makeText(this, "Your data is saved", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            Toast.makeText(this, "Your data is not saved", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        return dialog
    }





    private fun dataAndTime() {

        //static date Format
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val currentDateFormat: String = simpleDateFormat.format(Date())
        addScreenBinding.txtDate.text = currentDateFormat

        selectedDateValue = currentDateFormat

        //Dynamic date Format
        var cal: Calendar = Calendar.getInstance()
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                addScreenBinding.txtDate.text = sdf.format(cal.getTime())
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        addScreenBinding.txtDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddScreenActivty,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })
        selectedDateValue = currentDateFormat



//        addScreenBinding.txtTime.setOnClickListener {
//            val timePicker = TimePickerDialog(this, timePickerDialogListener, 12, 10, false)
//            timePicker.show()
//        }
    }

    private fun paymentMode() {

        var paymentList = ArrayList<String>()

        paymentList.add("Cash")
        paymentList.add("Credit Card")
        paymentList.add("Debit Card")
        paymentList.add("Net Banking")
        paymentList.add("Cheque")

        addScreenBinding.cdMode.setOnClickListener {

            val dialog = Dialog(this)

            val dialogPayBinding = DialogPaymentBinding.inflate(layoutInflater)
            dialog.setContentView(dialogPayBinding.root)


            var adapter1 = DialogPaymentAdapter(paymentList, { modeName ->
                selectedMode = modeName
                addScreenBinding.txtModeSelected.text = modeName
            })
            val manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dialogPayBinding.recycleDialog.layoutManager = manger
            dialogPayBinding.recycleDialog.adapter = adapter1


            dialogPayBinding.btnSet.setOnClickListener {

                Toast.makeText(this, "Your data is save", Toast.LENGTH_SHORT).show()



                dialog.dismiss()
            }
            dialogPayBinding.btnCancel.setOnClickListener {

                Toast.makeText(this, "Your data is not Save", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }
    }

}
