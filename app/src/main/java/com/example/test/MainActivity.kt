package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val today = findViewById<TextView>(R.id.today) as TextView
        val tomorrow = findViewById<TextView>(R.id.tomorrow) as TextView
        val day_after_tomorrow = findViewById<TextView>(R.id.totomorrow) as TextView

        val field = arrayOf(today, tomorrow, day_after_tomorrow)

        "https://weather.tsukumijima.net/api/forecast/city/110010".httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val data = String(response.data)
                    val jsonObj = JSONObject(data)
                    val forecasts = jsonObj.getJSONArray("forecasts")
                    for(i in 0 until forecasts.length()){
                        val day = forecasts.getJSONObject(i)
                        val date = day.getString("date")
                        val telop = day.getString("telop")
                        println(day)
                        println("${date} : ${telop}")
                        field[i].text = "${date} : ${telop}"
                    }
                    //println(forecasts)
                    //textView.text = forecasts
                }
                is Result.Failure -> {
                    val ex = result.getException()
                    println("aaaaaaqa")
                    println(ex)
                    today.text = "cant connection"
                }
            }
        }
    }
}