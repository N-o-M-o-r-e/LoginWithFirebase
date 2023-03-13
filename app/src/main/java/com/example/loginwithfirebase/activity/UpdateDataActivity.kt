package com.example.loginwithfirebase.activity

import android.util.Log
import com.example.loginwithfirebase.databinding.ActivityUpdateDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDataActivity : BaseActivityWithoutDataBiding<ActivityUpdateDataBinding>(ActivityUpdateDataBinding::inflate){

    private lateinit var database : DatabaseReference
    override fun initData() {
        
    }

    override fun initView() {
        binding.btnUpdateData.setOnClickListener {
            val edtName = binding.edtName.text.toString()
            val edtAge = binding.edtAge.text.toString()
            val edtEmail = binding.edtEmail.text.toString()
            val edtSdt = binding.edtSdt.text.toString()

            updateData(edtName, edtAge, edtEmail, edtSdt)



        }
    }

    private fun updateData(edtName: String, edtAge: String, edtEmail: String, edtSdt: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        val user = mapOf<String, String>(
            "name" to edtName,
            "age" to edtAge,
            "email" to edtEmail,
            "sdt" to edtSdt
        )
        database.child(edtName).updateChildren(user).addOnSuccessListener {
            binding.edtName.text.clear()
            binding.edtAge.text.clear()
            binding.edtEmail.text.clear()
            binding.edtSdt.text.clear()

            Log.e("TTTT", database.toString())
        }.addOnFailureListener{
            Log.e("FFFF",database.toString())
        }
    }


    override fun listenLiveData() {
        
    }

    override fun listeners() {
        
    }
}