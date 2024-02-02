package com.example.myexpensemanger.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myexpensemanger.modelclass.CategoryModelClass
import com.example.myexpensemanger.modelclass.IncomeExpenseModelClass

class SqLiteHelperData(context: Context) : SQLiteOpenHelper(context, "categoryDB", null, 1) {
    var categoryList = ArrayList<CategoryModelClass>()
    var transactionList = ArrayList<IncomeExpenseModelClass>()


    override fun onCreate(db: SQLiteDatabase?) {
        var sql =
            "create table categoryTB(category_Id integer Primary Key Autoincrement,category text)"
        db?.execSQL(sql)


        var sql2 =
            "create table transactTb(transact_Id integer Primary Key Autoincrement,amount text,selectedCategory text,selectedDateValue text ,selectedMode text,note text,page text)"
        db?.execSQL(sql2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun insertCategory(categoryName: String) {
        var db = writableDatabase

        var c = ContentValues()

        c.put("category", categoryName)

        db.insert("categoryTB", null, c)
    }

    fun displayCategory(): ArrayList<CategoryModelClass> {
        categoryList.clear()

        var db = readableDatabase
        var sql = "select * from categoryTB"
        val cursor = db.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val categoryName = cursor.getString(1)

                var model = CategoryModelClass(categoryName)

                categoryList.add(model)
            } while (cursor.moveToNext())
        } else {
            Log.e("TAG", "DisplayData: " + "No Data Found")
        }
        return categoryList

    }


    fun insertTransact(
        amount: String,
        selectedCategory: String,
        selectedDateValue: String,
        selectedMode: String,
        note: String,
        page: String
    ) {
        var db1 = writableDatabase
        var addData = ContentValues()
        addData.put("amount", amount)
        addData.put("selectedCategory", selectedCategory)
        addData.put("selectedDateValue", selectedDateValue)
        addData.put("selectedMode", selectedMode)
        addData.put("note", note)
        addData.put("page", page)

        db1.insert("transactTb", null, addData)
    }


    fun displayTransact(): ArrayList<IncomeExpenseModelClass> {
        transactionList.clear()

        var db1 = readableDatabase
        var sql1 = "select * from transactTb "
        var cursor1 = db1.rawQuery(sql1, null)
        if (cursor1.count > 0) {
            cursor1.moveToFirst()
            do {
                var id = cursor1.getInt(0)
                var amount = cursor1.getString(1)
                var selectedCategory = cursor1.getString(2)
                var selectedDateValue = cursor1.getString(3)
                var selectedMode = cursor1.getString(4)
                var note = cursor1.getString(5)
                var page = cursor1.getString(6)

                var modle1 = IncomeExpenseModelClass(
                    id,
                    amount,
                    selectedCategory,
                    selectedDateValue,
                    selectedMode,
                    note,
                    page
                )

                transactionList.add(modle1)
            } while (cursor1.moveToNext())
        } else {
            Log.e("TAG", "DisplayData: " + "No Data Found")
        }
        return transactionList
    }

    fun updateRecord(
        amount: String?,
        selectedCategory: String?,
        selectedDateValue: String?,
        selectedMode: String?,
        note: String?,
        page: String?,
        id: Int
    ) {

        val update = writableDatabase
        val updateSql =
            "update transactTb set amount='$amount' ,selectedCategory='$selectedCategory',selectedDateValue='$selectedDateValue',selectedMode='$selectedMode', note='$note',page='$page'  where transact_Id='$id' "
        update.execSQL(updateSql)


    }

    fun deleteData(id: Int) {
        var db = writableDatabase
        var delete = "delete from transactTb where transact_Id='$id' "
        db.execSQL(delete)

    }
}