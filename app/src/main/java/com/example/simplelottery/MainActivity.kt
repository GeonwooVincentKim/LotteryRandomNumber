package com.example.simplelottery

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.firstNumber),
            findViewById<TextView>(R.id.secondNumber),
            findViewById<TextView>(R.id.thirdNumber),
            findViewById<TextView>(R.id.fourthNumber),
            findViewById<TextView>(R.id.fifthNumber),
            findViewById<TextView>(R.id.sixthNumber),
        );
    }

    private val saveButton: Button by lazy{
        findViewById<Button>(R.id.saveButton)
    }

    private var didRun = false;

    private var pickNumberSet = hashSetOf<Int>()

//    private var sharePreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        saveButton()
        initClearButton()
    }

    private fun initRunButton(): Unit {
        runButton.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                backgroundColor(number, textView)
            }

            Log.d("MainActivity", list.toString());
        }
    }


    private fun initAddButton(): Unit {
        addButton.setOnClickListener {
            /* Before select Number */
            if (didRun) {
                Toast.makeText(this, "초기화 후에 실행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            /* After Select Number */
            val textView = numberTextViewList[pickNumberSet.size];
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            backgroundColor(numberPicker.value, textView)

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }

            didRun = false
        }
    }

//    @SuppressLint("CommitPrefEdits")
    private fun saveButton(): Unit{
        saveButton.setOnClickListener{
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                backgroundColor(number, textView)
            }

            Log.d("MainActivity", list.toString());

//            Contact
//            val userLocalData = this.getSharedPreferences(shared)
//            val editor: SharedPreferences.Editor = sharePreferences.edit()
//            val separator = ","
//            val getAsString = list.joinToString(separator)
//            editor.putString("numberList", getAsString)
//            editor.apply()
        }
//
//        saveButton.setOnClickListener{
////            var list = saveRandomList()
//            val saveList = numberTextViewList;
//            Log.d("MainActivity", saveList.toString());
//        }
    }


    private fun backgroundColor(number: Int, textView: TextView) {
        when (number) {
            in 1..10 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 11..20 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_green)
            in 21..30 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 31..40 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_red)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_plum)
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    // Exclude selected numbers
                    if (pickNumberSet.contains(i)) {
                        continue
                    }

                    this.add(i)
                }
            }

        /* Mix the number */
        numberList.shuffle()

        /*
            Extract the Number from numberList
            - The number that the random function gets
            - It gets 6 Items from numberList (Maximum)
            - It gets the number of items from numberList
              it could be 0 Items, but get randomized number as the length of numberList
            - Print Randomized number without users selection
        */
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted();
    }

//    private fun saveRandomList(): List<Int>{
//        var numberList =
//    }

//    private fun
}
