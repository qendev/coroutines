package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //create a constant
    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //simulate the background requests using Coroutines
        button.setOnClickListener {
            //launch a coroutine using a CoroutineScope
            //eg.s of CoroutineScope;IO,Main,Default
            //'launch' is a coroutine builder
            CoroutineScope(IO).launch {
                fakeApiRequest()

            }


        }
    }
    //to the text in the TextView get new input and append it
    private fun setNewText(input:String){
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    //to set Text on MainTHread
    private suspend fun setTextOnMainTHread(input: String){
        //fire up a new coroutine to work on the main thread
        withContext(Main){
            setNewText(input)

        }

    }
    //to execute the suspend MyApiResult1 fun to get a result
    private suspend fun fakeApiRequest(){
        val  result1 = MyApiResult1()
        println("TestingCoroutine: $result1")
        setTextOnMainTHread(result1)

        val result2 =MyApiResult2()
        setTextOnMainTHread(result2)
    }
    //method returns something Asychronously(either network request or to Room Persistent Library)
    private suspend fun MyApiResult1(): String{
        logThread("MyApiResult1")
        delay(500)
        return RESULT_1
    }

    private suspend fun MyApiResult2():String{
        logThread("MyApiResult2")
        delay(500)
        return RESULT_2
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
