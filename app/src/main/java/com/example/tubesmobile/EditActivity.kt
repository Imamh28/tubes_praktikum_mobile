package com.example.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tubesmobile.data.CountryDetail
import com.example.tubesmobile.data.Response
import com.example.tubesmobile.databinding.ActivityEditBinding
import com.example.tubesmobile.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryData: CountryDetail? = intent.getParcelableExtra("country_detail")

        binding.editTaskName.setText(countryData!!.task_name)
        binding.editSubjectName.setText(countryData.subject_name)
        binding.editTaskDeadline.setText((countryData.task_deadline!!).toString());
        binding.editTaskDeadline.setText(countryData.task_description)

        binding.updateButton.setOnClickListener {
            updateCountryDetail(countryData.countryId!!)
        }
    }

    private fun updateCountryDetail(countryId: Int) {
        val inputName = binding.editTaskName.text.toString().trim()
        val inputContinent = binding.editSubjectName.text.toString().trim()
        val inputPopulation = binding.editTaskDeadline.text.toString().trim()
        val inputDescription = binding.editTaskDesc.text.toString().trim()

        //validation data
        if (inputName.isEmpty()) {
            binding.editTaskName.error = "Field is empty"
        }
        if (inputContinent.isEmpty()) {
            binding.editSubjectName.error = "Field is empty"
        }
        if (inputPopulation.isEmpty()) {
            binding.editTaskDeadline.error = "Field is empty"
        }
        if (inputDescription.isEmpty()) {
            binding.editTaskDesc.error = "Field is empty"
        }

        if (inputName.isNotEmpty() && inputContinent.isNotEmpty() && inputPopulation.isNotEmpty() && inputDescription.isNotEmpty()) {
            val updatedId = countryId.toString()
            RetrofitClient.instance.updateCountryDetail(updatedId, inputName, inputContinent, inputPopulation, inputDescription)
                .enqueue(object: Callback<Response> {
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(this@EditActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                            else {
                                Toast.makeText(this@EditActivity, resp.message, Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@EditActivity, MainActivity::class.java))

                                this@EditActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@EditActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("EDIT COUNTRY (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@EditActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("EDIT COUNTRY FAIL", t.toString())
                    }
                })
        } else {
            Toast.makeText(this@EditActivity, "Fail editing country data, field(s) is empty!", Toast.LENGTH_LONG).show()
        }
    }
}