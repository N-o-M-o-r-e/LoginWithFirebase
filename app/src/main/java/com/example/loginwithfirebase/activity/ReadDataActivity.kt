package com.example.loginwithfirebase.activity

import android.util.Log
import com.example.loginwithfirebase.databinding.ActivityReadDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadDataActivity : BaseActivityWithoutDataBiding<ActivityReadDataBinding>(ActivityReadDataBinding::inflate){
    private lateinit var database : DatabaseReference

    override fun initData() {
        
    }

    override fun initView() {
        binding.btnReadData.setOnClickListener {

            val userName : String = binding.edName.text.toString()
            if (userName.isNotEmpty()){
                readData(userName)
            }
            else{
                Log.e("AAA ", "No Data")
            }
        }

    }

    private fun readData(userName: String) {


        database = FirebaseDatabase.getInstance().getReference("User")


        database.child(userName).get().addOnSuccessListener {
            if(it.exists()){

                val age = it.child("age").value
                val email = it.child("email").value
                val sdt = it.child("sdt").value

                binding.edName.text.clear()
                binding.Age.text = age.toString()
                binding.Email.text = email.toString()
                binding.Phone.text = sdt.toString()

            }else{
                Log.e("BBB ", "No Data")

            }

        }.addOnFailureListener {
            Log.e("CCC ", "No Data")

        }

    }

    override fun listenLiveData() {
        
    }

    override fun listeners() {
        
    }

}