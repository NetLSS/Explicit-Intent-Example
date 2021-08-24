package com.lilcode.example.explicitintentexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.lilcode.example.explicitintentexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = requireNotNull(_binding)

    private val request_code = 5

    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 결과를 받아오는 액티비티 런처는 되도록 onCreate() 부분에 정의하자. 바로 런치하면 에러 발생가능.
        getResultText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it?.resultCode == RESULT_OK && it.resultCode == request_code) {
                    it.data?.let { data: Intent ->
                        if (data.hasExtra("returnData")) {
                            val returnString = data.extras?.getString("returnData")
                            binding.textView1.text = returnString
                        }
                    }
                }
            }
    }

    fun sendText(view: View) {
        val intent = Intent(this, ActivityB::class.java)
        val myString = binding.editText1.text.toString()
        intent.putExtra("qString", myString)

        // 일반 실행
        // startActivity(intent)

        // 서브 엑티비티로 실행 (deprecated)
        //startActivityForResult(intent, request_code)

        // 공식문서에서 최근 권장하는 방법
        getResultText.launch(intent)
    }

    // 서브 엑티비티로 실행 (deprecated)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == request_code && resultCode == RESULT_OK) {
            data?.let {
                if (it.hasExtra("returnData")) {
                    val returnString = it.extras?.getString("returnData")
                    binding.textView1.text = returnString
                }
            }
        }
    }
}