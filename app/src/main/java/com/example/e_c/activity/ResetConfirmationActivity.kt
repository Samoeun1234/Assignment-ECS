package com.example.ecomerce_store_ass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomerce_store_ass.databinding.ActivityResetConfirmationBinding

class ResetConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnLoginBtn.setOnClickListener {
            startActivity(Intent(this, SignInEmailActivity::class.java))
        }
    }
}