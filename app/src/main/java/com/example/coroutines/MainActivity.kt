package com.example.coroutines

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute : Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            lifecycleScope.launch {
                execute("Test Executed Successfully")
            }
        }
    }

    private suspend fun execute(result: String) {
        withContext(Dispatchers.IO) {
            for (i in 1..100000) {
                Log.e("Delay : " , "" + i)
            }
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this)
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog?.show()
    }

    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
}