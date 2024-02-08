package com.example.myexpensemanger.modelclass



data class CategoryModelClass(var categoryName: String)

data class IncomeExpenseModelClass(

    var id: Int,
    var amount: String,
    var selectedCategory: String,
    var date: String,
    var mode: String,
    var note: String,
    var page: String
)