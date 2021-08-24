package com.lilcode.example.explicitintentexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lilcode.example.explicitintentexample.databinding.ActivityBBinding
import com.lilcode.example.explicitintentexample.databinding.ActivityMainBinding

class ActivityB : AppCompatActivity() {
    var _binding: ActivityBBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras ?: return

        val qString = extras.getString("qString")
        binding.textView2.text = qString
    }
}