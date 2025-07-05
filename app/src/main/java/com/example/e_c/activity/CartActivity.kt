package com.example.e_c.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_c.Adapter.CartAdapter
import com.example.e_c.Helper.ChangeNumberItemsListener
import com.example.e_c.Helper.ManagementCart
import com.example.e_c.R
import com.example.e_c.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagementCart
    private var tax: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cart)

        managmentCart = ManagementCart(this)
        setVariable()
        initCartList()
        calculateCart()
        checkOut()
    }

    private fun setVariable(){
        binding.backBtn.setOnClickListener{finish()}
    }

    private fun initCartList(){
        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter=CartAdapter(managmentCart.getListCart(), this,object: ChangeNumberItemsListener{
            override fun onChanged() {
               calculateCart()
            }
        })

        with(binding){
            emptyTxt.visibility=if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility=if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }
    private fun calculateCart(){
        val percentTax = 0.02
        val delivery=1.0
        tax=Math.round((managmentCart.getTotalFee()*percentTax)*100)/100.0
        val total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) /100

        with(binding){
            totalFreeTax.text="$itemTotal"
            taxTxt.text="$tax"
            deliveryTax.text="$delivery"
            totalTxt.text="$total"

        }
    }

    private fun checkOut () {
        binding.checkOutBtn.setOnClickListener{
            val intent = Intent(this@CartActivity, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }
    }


}