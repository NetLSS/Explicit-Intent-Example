package com.lilcode.example.explicitintentexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lilcode.example.explicitintentexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun sendText(view: View) {
        val intent = Intent(this, ActivityB::class.java)
        val myString = binding.editText1.text.toString()
        intent.putExtra("qString", myString)
        startActivity(intent)
    }
}