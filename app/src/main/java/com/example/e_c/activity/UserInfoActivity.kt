package com.example.ecomerce_store_ass

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomerce_store_ass.databinding.ActivityUserInfoBinding


class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ageRanges = listOf("Under 18", "18-24", "25-34", "35-44", "45+")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ageRanges)
        binding.ageRangeSpinner.adapter = adapter

        binding.finishBtn.setOnClickListener {
            // Complete user onboarding
        }
    }
}