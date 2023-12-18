package com.example.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tubesmobile.data.CountryDetail
import com.example.tubesmobile.databinding.ActivityDetailBinding
import com.example.tubesmobile.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var list: CountryDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryId: String = intent.getStringExtra("country_id").toString()

        retrieveCountryDetail(countryId)

        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this@DetailActivity, EditActivity::class.java)
                    .putExtra("country_detail", list)
            )
        }

        binding.btnDelete.setOnClickListener {
            deleteCountry(countryId)
        }
    }

    private fun retrieveCountryDetail(countryId: String) {
        RetrofitClient.instance.getCountryDetail(countryId)
            .enqueue(object: Callback<CountryDetail> {
                override fun onResponse(call: Call<CountryDetail>, response: Response<CountryDetail>) {
                    if (response.code() == 200) {
                        list = response.body()!!
                        Log.d("GET COUNTRY DETAIL", list.toString())

                        binding.tvCountryName.text = list.countryName
                        binding.tvCountryArea.text = list.countryArea
                        binding.tvCountryPopulation.text = list.countryPopulation.toString()
                        binding.tvCountryDesc.text = list.countryDescription
                    } else {
                        Toast.makeText(this@DetailActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET COUNTRY ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<CountryDetail>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET COUNTRY ITEMS FAIL", t.toString())
                }
            })
    }

    private fun deleteCountry(countryId: String) {
        RetrofitClient.instance.deleteCountryDetail(countryId)
            .enqueue(object: Callback<com.example.mobile_programming_8.data.Response> {
                override fun onResponse(
                    call: Call<com.example.mobile_programming_8.data.Response>,
                    response: Response<com.example.mobile_programming_8.data.Response>
                ) {
                    if (response.code() == 200) {
                        val resp = response.body()

                        if (resp!!.error) Toast.makeText(this@DetailActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                        else {
                            Toast.makeText(this@DetailActivity, resp.message, Toast.LENGTH_SHORT).show()

                            startActivity(Intent(this@DetailActivity, MainActivity::class.java))

                            this@DetailActivity.finish()
                        }
                    } else {
                        Toast.makeText(this@DetailActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                        Log.d("DELETE COUNTRY (${response.code()})", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<com.example.mobile_programming_8.data.Response>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                    Log.d("DELETE COUNTRY FAIL", t.toString())
                }
            })
    }
}