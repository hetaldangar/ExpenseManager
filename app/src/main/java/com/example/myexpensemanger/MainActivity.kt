package com.example.myexpensemanger.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.myexpensemanger.databinding.ActivityMainBinding
import com.example.myexpensemanger.notification.MyReceiver

import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding  //activity binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root) //set xml file

        ScheduleAlarm()
        initView()

    //    notification()
    }








    private fun ScheduleAlarm() {
        val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this@MainActivity, MyReceiver::class.java).let { intent ->
            intent.action = "ALARM_ACTION"
            PendingIntent.getBroadcast(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

//         Set the alarm to start at 10 M every day
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 2)
            set(Calendar.SECOND, 0)


        }

        //         Schedule the alarm
        alarm.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )


       }

    private fun initView() {


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
