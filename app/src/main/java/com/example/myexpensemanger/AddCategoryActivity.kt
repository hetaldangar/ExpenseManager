package com.example.myexpensemanger.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myexpensemanger.adapter.AddCategoryAdapter
import com.example.myexpensemanger.databinding.ActivityAddCategoryBinding
import com.example.myexpensemanger.sqlite.SqLiteHelperData

class AddCategoryActivity : AppCompatActivity() {

    lateinit var addCategoryBinding: ActivityAddCategoryBinding  // activity biding

    lateinit var db: SqLiteHelperData   //define sqlite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCategoryBinding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(addCategoryBinding.root)  // set xml file

        db = SqLiteHelperData(this)   // set context class in sqlite
        initView()
    }

    private fun initView() {

        addCategoryBinding.imgBack.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)  //this activity to MainActivity Move
            startActivity(i)
            finish()
        }
        addCategoryBinding.imgDone.setOnClickListener {
            Toast.makeText(this, "Your category is added successfully", Toast.LENGTH_SHORT).show()
            var i = Intent(this, MainActivity::class.java)   //this activity to MainActivity Move
            startActivity(i)
            finish()
        }
//        addCategoryBinding.btnAdd.setOnClickListener {
//            var categoryName = addCategoryBinding.edtAddCategory.text.toString()  //EditText value store this  variable
//
//            db.insertCategory(categoryName)  //data base class in store value
//
//
//
//            addCategoryBinding.edtAddCategory.setText("")  //clear Edit text value
//        }
        val list = db.displayCategory()  //show data base in this class

        val adapter = AddCategoryAdapter()  //set adapter class
        val manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        addCategoryBinding.recycleCategory.layoutManager = manger
//        addCategoryBinding.recycleCategory.adapter = adapter

        adapter.updateData(list)  //adapter class in create new function and pass parameter
    }
}