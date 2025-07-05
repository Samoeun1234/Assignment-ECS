package com.example.e_c.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.e_c.Adapter.BrandAdapter
import com.example.e_c.Adapter.PopularAdapter
import com.example.e_c.Adapter.SliderAdapter
import com.example.e_c.Model.SliderModel
import com.example.e_c.ViewModel.MainViewModel
import com.example.e_c.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    private val bannerSlideDelay = 3000L
    private val bannerSlideHandler = Handler()
    private var currentBannerPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Setup all views
        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
        loadUserInfo()
        setupProfileNavigation()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE

        viewModel.banners.observe(this) { items ->
            setupBannerSlider(items)
            binding.progressBarBanner.visibility = View.GONE
        }

        viewModel.loadBanners()
    }

    private val bannerSlideRunnable = object : Runnable {
        override fun run() {
            val adapter = binding.viewpageSlider.adapter
            if (adapter != null && adapter.itemCount > 0) {
                currentBannerPage = (currentBannerPage + 1) % adapter.itemCount
                binding.viewpageSlider.currentItem = currentBannerPage
                bannerSlideHandler.postDelayed(this, bannerSlideDelay)
            }
        }
    }

    private fun setupBannerSlider(images: List<SliderModel>) {
        binding.viewpageSlider.adapter = SliderAdapter(images, binding.viewpageSlider)
        binding.viewpageSlider.clipToPadding = false
        binding.viewpageSlider.clipChildren = false
        binding.viewpageSlider.offscreenPageLimit = 3
        binding.viewpageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewpageSlider.setPageTransformer(transformer)

        if (images.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.viewpageSlider)
        }

        currentBannerPage = 0
        bannerSlideHandler.postDelayed(bannerSlideRunnable, bannerSlideDelay)
    }

    private fun initBrand() {
        binding.progressBarBranch.visibility = View.VISIBLE

        viewModel.brands.observe(this) {
            binding.viewBranch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBranch.adapter = BrandAdapter(it)
            binding.progressBarBranch.visibility = View.GONE
        }

        viewModel.loadBrand()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE

        viewModel.popular.observe(this) {
            binding.viewPopular.layoutManager = GridLayoutManager(this, 2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        }

        viewModel.loadPopular()
    }

    // Load full name from Firestore
    private fun loadUserInfo() {
        val uid = auth.currentUser?.uid ?: return

        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                val firstName = document.getString("firstName") ?: ""
                val lastName = document.getString("lastName") ?: ""
                val fullName = "$firstName $lastName".trim()

                binding.textView5.text = "$fullName"
            }
            .addOnFailureListener {
                binding.textView5.text = "Admin"
            }
    }

    private fun setupProfileNavigation() {
        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerSlideHandler.removeCallbacks(bannerSlideRunnable)
    }
}
