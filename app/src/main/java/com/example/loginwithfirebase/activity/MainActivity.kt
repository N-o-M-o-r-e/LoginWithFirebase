package com.example.loginwithfirebase.activity

import android.util.Log
import com.example.loginwithfirebase.databinding.ActivityMainBinding
import com.example.loginwithfirebase.models.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : BaseActivityWithoutDataBiding<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var database : DatabaseReference
    override fun initData() {
        
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener{
            val name = binding.edFullName.text.toString().trim()
            val age = binding.edAge.text.toString().trim()
            val phoneNumber = binding.edSdt.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()

            database  = FirebaseDatabase.getInstance().getReference("User")
            val user = User(name,age.toInt(),phoneNumber,email)

            database.child(name).setValue(user).addOnSuccessListener {
                binding.edFullName.text.clear()
                binding.edAge.text.clear()
                binding.edSdt.text.clear()
                binding.edEmail.text.clear()
                Log.e("ok", database.toString())
            }.addOnFailureListener{
                Log.e("no", database.toString())
                Log.e("noo", user.toString())
                Log.e("ex", it.printStackTrace().toString())
            }
        }
    }

    override fun listenLiveData() {
        
    }

    override fun listeners() {
        
    }
}
