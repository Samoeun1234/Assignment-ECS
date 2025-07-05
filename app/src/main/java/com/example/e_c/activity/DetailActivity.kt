package com.example.e_c.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_c.Adapter.ColorAdapter
import com.example.e_c.Adapter.SizeAdapter
import com.example.e_c.Adapter.SliderAdapter
import com.example.e_c.Helper.ManagementCart
import com.example.e_c.Model.ItemsModel
import com.example.e_c.Model.SliderModel
import com.example.e_c.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOder = 1
    private lateinit var managerCart: ManagementCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        managerCart= ManagementCart(this)
        getBundle()
        banners()
        initLists()
    }

    private fun initLists(){
        val sizeList = ArrayList<String>()
        for(size in item.size){
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter=SizeAdapter(sizeList)
        binding.sizeList.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList=ArrayList<String>()
        for(imageUrl in item.picUrl){
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun banners(){
        var sliderItem=ArrayList<SliderModel>()
        for (imageUrl in item.picUrl){
            sliderItem.add(SliderModel(imageUrl))
        }
        binding.slider.adapter=SliderAdapter(sliderItem,binding.slider)
        binding.slider.clipToPadding=true
        binding.slider.clipChildren=true
        binding.slider.offscreenPageLimit=1

        if(sliderItem.size > 1){
            binding.dotsIndicator.visibility=View.VISIBLE
            binding.dotsIndicator.attachTo(binding.slider)
        }
    }
//    private fun getBundle(){
//        item=intent.getParcelableExtra("object") !!
//        binding.titleTxt.text=item.title
//        binding.descriptionTxt.text=item.description
//        binding.priceTxt.text="$"+item.price
//        binding.ratingTxt.text="${item.rating}Rating"
//        binding.addToCartBtn.setOnClickListener{
//            item.numberInCart=numberOder
//            managerCart.insertFood(item)
//        }
//        binding.backBtn.setOnClickListener{finish()}
//        binding.cartBtn.setOnClickListener{
//            startActivity(Intent(this@DetailActivity, DetailActivity::class.java))
//
//        }
//
//    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$" + item.price
        binding.ratingTxt.text = "${item.rating} Rating"

        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOder
            managerCart.insertFood(item)
        }

        binding.backBtn.setOnClickListener { finish() }

        // ✅ Fix: Go to CartActivity instead of DetailActivity
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }

}