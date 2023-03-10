package com.example.ca6api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val retroFitBuilder by lazy{
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIinterface::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun getJsonData(){
        val jsondata = retroFitBuilder.getAPIData()
        jsondata.enqueue(object : Callback<List<DataAPIItem>?> {
            override fun onResponse(
                call: Call<List<DataAPIItem>?>,
                response: Response<List<DataAPIItem>?>
            ) {
                val responseReturn = response.body()!!
                val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = RVAdapter(responseReturn)
            }
            override fun onFailure(call: Call<List<DataAPIItem>?>, t: Throwable){
                Log.d("APIFAIL", "message" + t.message)
            }
        })
    }
}