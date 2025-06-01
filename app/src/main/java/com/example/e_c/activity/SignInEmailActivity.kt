package com.example.e_c

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomerce_store_ass.databinding.ActivitySignInEmailBinding

class SignInEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            startActivity(Intent(this, SignInPasswordActivity::class.java))
        }

        binding.createAccountLink.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }
}
