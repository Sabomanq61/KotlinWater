package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    private var waterMeasure: Int = 0;
    private var water : Int = 0; // Храним кол воды в мл.

    private val myPreferences = "myPref";
    private val value = "water";
    private val dayStr = "date";
    private val monthStr = "month";
    private val yearStr = "year";
    var sharedPreferences: SharedPreferences? = null;

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentDate = LocalDateTime.now();
        var day:Int = currentDate.dayOfMonth;
        var month:Int = currentDate.monthValue;
        var year:Int = currentDate.year;

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        val editText = findViewById<EditText>(R.id.editTextNumberDecimal);
        var waterView = findViewById<TextView>(R.id.waterView);
        if(sharedPreferences!!.contains(dayStr))
        {
            day = sharedPreferences!!.getInt(dayStr, currentDate.dayOfMonth);
        }
        if(sharedPreferences!!.contains(dayStr))
        {
            month = sharedPreferences!!.getInt(monthStr, currentDate.monthValue);
        }
        if(sharedPreferences!!.contains(dayStr))
        {
            year = sharedPreferences!!.getInt(yearStr, currentDate.year);
        }

        if( day == currentDate.dayOfMonth && month == currentDate.monthValue && year == currentDate.year )
        {
            water = sharedPreferences!!.getInt(value,0);
            val liter:Int = water / 1000;
            val ml:Int = water % 1000;
            waterView.text = "$liter л $ml мл";
        }
        else
        {
            save(day, month, year, water); // Перезаписали настройки.
        }

        val waterMeasures = arrayOf("л","мл");

        val spinner = findViewById<Spinner>(R.id.spinner);

        if(spinner!=null) {
            val waterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, waterMeasures);
            spinner.adapter = waterAdapter;

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    waterMeasure = position;
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            addWater(editText.text.toString().toInt());
            val liter:Int = water / 1000;
            val ml:Int = water % 1000;
            waterView.text = "$liter л $ml мл";
            save(water);
        }
        findViewById<Button>(R.id.del_button).setOnClickListener {
            subWater(editText.text.toString().toInt());
            val liter:Int = water / 1000;
            val ml:Int = water % 1000;
            waterView.text = "$liter л $ml мл";
            save(water);
        }
    }

    fun toastMe(view: View) {
        val myRandom = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
        myRandom.show();
    }

    private fun addWater(amount:Int) {

        if(waterMeasure == 0)
            water += amount * 1000;
        else
            water += amount;
    }

    private fun subWater(amount:Int) {
        if(amount > water) {
            water = 0;
            return;
        }
        if(waterMeasure == 0)
            water -= amount * 1000;
        else
            water -= amount;
    }

    private fun save(day:Int, month:Int, year:Int, waterValue:Int)
    {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit();
        editor.putInt(dayStr, day);
        editor.putInt(monthStr, month);
        editor.putInt(yearStr, year);
        editor.putInt(value, waterValue);
        editor.apply();
    }

    private fun save( waterValue:Int)
    {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit();
        editor.putInt(value, waterValue);
        editor.apply();
    }

}


























