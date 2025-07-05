package com.example.e_c.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.e_c.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val IMAGE_PICK_CODE = 1000

    private val storageRef = FirebaseStorage.getInstance().reference
    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Back button
        binding.backBtn.setOnClickListener { finish() }

        // Sign out
        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Load user info
        loadUserInfo()

        // Pick image
        binding.cameraOverlay.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun loadUserInfo() {
        val user = auth.currentUser ?: return
        binding.fullNameInput.setText(user.displayName)
        binding.userName.text = user.displayName ?: "User"
        binding.emailInput.setText(user.email)
        binding.passwordInput.setText("********")

        // Load profile image from Realtime Database
        databaseRef.child("users").child(user.uid).child("profileImageUrl")
            .get()
            .addOnSuccessListener { snapshot ->
                val imageUrl = snapshot.getValue(String::class.java)
                if (!imageUrl.isNullOrEmpty()) {
                    Glide.with(this).load(imageUrl).into(binding.profileImage)
                }
            }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                binding.profileImage.setImageURI(imageUri)
                uploadImageToFirebaseStorage(imageUri)
            }
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val user = auth.currentUser ?: return
        val filePath = "profile_images/${user.uid}.jpg"
        val fileRef = storageRef.child(filePath)

        fileRef.putFile(imageUri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    saveImageUrlToRealtimeDatabase(user.uid, uri.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveImageUrlToRealtimeDatabase(uid: String, imageUrl: String) {
        databaseRef.child("users").child(uid).child("profileImageUrl")
            .setValue(imageUrl)
            .addOnSuccessListener {
                Toast.makeText(this, "Image uploaded and saved", Toast.LENGTH_SHORT).show()
                Glide.with(this).load(imageUrl).into(binding.profileImage)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save URL: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
