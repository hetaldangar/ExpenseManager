package com.example.myexpensemanger.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import com.example.myexpensemanger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding  //activity binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root) //set xml file

        initView()
    }

    private fun initView() {
//        mainBinding.imgMenu.setOnClickListener{
//            mainBinding.drawerLayout.openDrawer(GravityCompat.START)
//        }

        var title_income="Add Income"    // define title
        mainBinding.cdIncome.setOnClickListener {
            var income = Intent(this, AddScreenActivty::class.java)
            income.putExtra("Page","income")  //set Key and pass second activity
            income.putExtra("title",title_income)   //set title
            startActivity(income)
        }

        var title_expense="Add Expense"   // define title
        mainBinding.cdExpenses.setOnClickListener {
            var expense = Intent(this, AddScreenActivty::class.java)
            expense.putExtra("Page","expense")  //set Key and pass second activity
            expense.putExtra("title",title_expense)   //set title
            startActivity(expense)
        }
        mainBinding.cdTransaction.setOnClickListener {
            var transaction = Intent(this, TransactionActivity::class.java)
            startActivity(transaction)
        }

        mainBinding.cdCategory.setOnClickListener {
            var i = Intent(this, AddCategoryActivity::class.java)
            startActivity(i)
        }
    }
}