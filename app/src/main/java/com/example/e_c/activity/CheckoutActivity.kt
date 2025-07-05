package com.example.e_c.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.e_c.Helper.ManagementCart
import com.example.e_c.R
import com.example.e_c.databinding.ActivityCheckoutBinding

class CheckoutSuccessActivity : BaseActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var managementCart: ManagementCart;

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)
        managementCart.clearCart();

        // Load animations
        val scaleAnim = AnimationUtils.loadAnimation(this, R.drawable.scale_up)
        val fadeAnim = AnimationUtils.loadAnimation(this, R.drawable.fade_in)

        // Apply animations
        binding.successIcon.startAnimation(scaleAnim)
        binding.successMessage.startAnimation(fadeAnim)

        // Navigate back to home
        binding.backHomeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
