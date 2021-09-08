package com.example.simplelottery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy{
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy{
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy{
        listOf<TextView>(
            findViewById<TextView>(R.id.firstNumber),
            findViewById<TextView>(R.id.secondNumber),
            findViewById<TextView>(R.id.thirdNumber),
            findViewById<TextView>(R.id.fourthNumber),
            findViewById<TextView>(R.id.fifthNumber),
            findViewById<TextView>(R.id.sixthNumber),
        );
    }

    private var didRun = false;

    private var pickNumberSet = hashSetOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }

    private fun initRunButton(): Unit{
        runButton.setOnClickListener{
            val list = getRandomNumber()

            Log.d("MainActivity", list.toString());
        }
    }

    private fun initAddButton(): Unit{
        addButton.setOnClickListener{
            /* Before select Number */
            if(didRun){
                Toast.makeText(this, "초기화 후에 실행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5){
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            /* After Select Number */
            val textView = numberTextViewList[pickNumberSet.size];
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    this.add(i)
                }
            }

        /* Mix the number */
        numberList.shuffle()

        /*
            Extract the Number from numberList
            - The number that the random function gets
            - It gets 6 Items from numberList
        */
        val newList = numberList.subList(0, 6)

        /*
        * Return sorted list
        * */
        /*
        for (i in newList) {
            if (!newList.equals(newList[i])) {
                break;
            }
        }*/

        return newList.sorted();
    }
}
