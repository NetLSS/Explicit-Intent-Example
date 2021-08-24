# 엑티비티 실행 시키기 및 결과 받기 

```kotlin
    private lateinit var getResultText: ActivityResultLauncher<Intent> // 👈

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 결과를 받아오는 액티비티 런처는 되도록 onCreate() 부분에 정의하자. 바로 런치하면 에러 발생가능.
        getResultText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { // 👈
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
```

```kotlin
    fun sendText(view: View) {
        val intent = Intent(this, ActivityB::class.java)
        val myString = binding.editText1.text.toString()
        intent.putExtra("qString", myString)

        // 일반 실행
        // startActivity(intent)

        // 서브 엑티비티로 실행 (deprecated)
        //startActivityForResult(intent, request_code)

        // 공식문서에서 최근 권장하는 방법
        getResultText.launch(intent) // 👈
    }
```

- 엑티비티 B 에서 

```kotlin

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

    fun returnText(view: View) {
        finish() // 👈
    }

    override fun finish() {
        //val data = Intent()
        val data = Intent(this, MainActivity::class.java) // 👈
        val returnString = binding.editText2.text.toString()
        data.putExtra("returnData", returnString)
        setResult(RESULT_OK, data) // 👈
        super.finish() // 👈
    }
}
```

## Reference

- https://developer.android.com/reference/kotlin/androidx/activity/result/contract/ActivityResultContracts