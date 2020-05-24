package com.black.multi.videosample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.black.multi.videosample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val adapter = RvAdapter(this,IRecycleViewCallback<String> { var1, var2 ->
//            Toast.makeText(this,"aa",Toast.LENGTH_SHORT).show()
//        })
//        val list = arrayListOf("aa","bb","cc","dd") as List<String>
//        adapter.setData(list)
//        rv.adapter =adapter
    }

}
