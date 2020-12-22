package com.toune.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.toune.verifycodeinputview.DLVerifyCodeInputView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dlVerifyCodeInputView = findViewById<DLVerifyCodeInputView>(R.id.dlVerifyCodeInputView)
        dlVerifyCodeInputView.finishListener = object :DLVerifyCodeInputView.FinishListener{
            override fun finish(code: String) {
                Toast.makeText(this@MainActivity,code,Toast.LENGTH_LONG).show()
            }
        }
    }
}