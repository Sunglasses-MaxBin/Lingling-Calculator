package com.sunglasses.linglingcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var inputText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById<TextView>(R.id.tv_display)

        //数字按钮
        val btnList = listOf(
            R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
            R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9
        )
        for(id in btnList){
            findViewById<Button>(id).setOnClickListener {
                val btn = it as Button
                appendText(btn.text.toString())
            }
        }

        findViewById<Button>(R.id.btn_dot).setOnClickListener { appendText(".") }
        findViewById<Button>(R.id.btn_add).setOnClickListener { appendText("+") }
        findViewById<Button>(R.id.btn_sub).setOnClickListener { appendText("-") }
        findViewById<Button>(R.id.btn_mul).setOnClickListener { appendText("×") }
        findViewById<Button>(R.id.btn_div).setOnClickListener { appendText("/") }

        //清空AC
        findViewById<Button>(R.id.btn_ac).setOnClickListener {
            inputText = ""
            refreshUi()
        }
        //删除
        findViewById<Button>(R.id.btn_del).setOnClickListener {
            if(inputText.isNotEmpty()){
                inputText = inputText.dropLast(1)
                refreshUi()
            }
        }
        //等于计算
        findViewById<Button>(R.id.btn_eq).setOnClickListener { calculate() }
    }

    private fun appendText(s:String){
        inputText += s
        refreshUi()
    }

    private fun refreshUi(){
        tvDisplay.text = inputText
    }

    private fun calculate(){
        try {
            var exp = inputText.replace("×","*")
            //原生Kotlin不支持eval，使用简易计算
            val result = evalExpression(exp)
            inputText = result.toString()
            refreshUi()
        }catch (e:Exception){
            inputText = "错误"
            refreshUi()
        }
    }

    //简易四则运算
    private fun evalExpression(str:String):Double{
        val numbers = mutableListOf<Double>()
        val ops = mutableListOf<Char>()
        var temp = ""
        for(c in str){
            if(c in "+-*/"){
                numbers.add(temp.toDouble())
                temp=""
                ops.add(c)
            }else{
                temp+=c
            }
        }
        numbers.add(temp.toDouble())

        //先乘除
        var i=0
        while(i < ops.size){
            val op = ops[i]
            if(op == '*' || op == '/'){
                val a = numbers[i]
                val b = numbers[i+1]
                val res = when(op){
                    '*' -> a*b
                    '/' -> a/b
                    else ->0.0
                }
                numbers.removeAt(i+1)
                numbers[i] = res
                ops.removeAt(i)
            }else{
                i++
            }
        }
        //再加减
        var sum = numbers[0]
        for(idx in ops.indices){
            val op = ops[idx]
            val v = numbers[idx+1]
            when(op){
                '+' -> sum +=v
                '-' -> sum -=v
            }
        }
        return sum
    }
}
