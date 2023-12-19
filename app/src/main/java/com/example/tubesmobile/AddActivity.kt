package com.example.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tubesmobile.data.Response
import com.example.tubesmobile.databinding.ActivityAddBinding
import com.example.tubesmobile.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback


class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            addCountryData()
        }
    }

    private fun addCountryData() {
        val inputName = binding.addTaskName.text.toString().trim()
        val inputContinent = binding.addSubjectName.text.toString().trim()
        val inputPopulation = binding.addTaskDeadline.text.toString().trim()
        val inputDescription = binding.addTaskDesc.text.toString().trim()

        //validation data
        if (inputName.isEmpty()) {
            binding.addTaskName.error = "Field is empty"
        }
        if (inputContinent.isEmpty()) {
            binding.addSubjectName.error = "Field is empty"
        }
        if (inputPopulation.isEmpty()) {
            binding.addTaskDeadline.error = "Field is empty"
        }
        if (inputDescription.isEmpty()) {
            binding.addTaskDesc.error = "Field is empty"
        }

        if (inputName.isNotEmpty() && inputContinent.isNotEmpty() && inputPopulation.isNotEmpty() && inputDescription.isNotEmpty()) {
            RetrofitClient.instance.addCountryDetail(inputName, inputContinent, inputPopulation, inputDescription)
                .enqueue(object: Callback<Response> {
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(this@AddActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                            else {
                                Toast.makeText(this@AddActivity, resp.message, Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@AddActivity, MainActivity::class.java))

                                this@AddActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@AddActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("ADD COUNTRY (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@AddActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("ADD COUNTRY FAIL", t.toString())
                    }
                })
        } else {
            Toast.makeText(this@AddActivity, "Fail adding country data, field(s) is empty!", Toast.LENGTH_LONG).show()
        }
    }
}