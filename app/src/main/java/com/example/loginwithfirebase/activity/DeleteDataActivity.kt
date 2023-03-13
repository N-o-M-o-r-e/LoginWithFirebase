package com.example.loginwithfirebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.loginwithfirebase.databinding.ActivityDeleteDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteDataActivity : BaseActivityWithoutDataBiding<ActivityDeleteDataBinding>(ActivityDeleteDataBinding::inflate){
    private lateinit var database : DatabaseReference

    override fun initData() {
        
    }

    override fun initView() {
        binding.btnDelete.setOnClickListener{
            var name  = binding.edtName.text.toString()
            if (name.isNotEmpty()){
                deleteData(name)
            }
        }
    }

    private fun deleteData(name: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(name).removeValue().addOnSuccessListener {
            binding.edtName.text.clear()
            Log.e("TTT", database.toString())
        }.addOnFailureListener{
            Log.e("FFFF", database.toString())
        }
    }

    override fun listenLiveData() {
        
    }

    override fun listeners() {
        
    }

}