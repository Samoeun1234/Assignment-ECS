package com.example.ecomerce_store_ass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomerce_store_ass.databinding.ActivitySignInPasswordBinding

class SignInPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            // Navigate to main/home screen or dashboard
        }

        binding.forgotPasswordLink.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}