package com.example.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubesmobile.adapter.CountryListAdapter
import com.example.tubesmobile.data.CountryItem
import com.example.tubesmobile.databinding.ActivityMainBinding
import com.example.tubesmobile.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveCountries()

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
    }

    private fun countryItemClicked(country: CountryItem) {
        val countryId = country.countryId.toString()

        startActivity(
            Intent(this@MainActivity, DetailActivity::class.java)
                .putExtra("country_id", countryId)
        )
    }

    private fun buildCountryList(countries: ArrayList<CountryItem>) {
        val countryAdapter = CountryListAdapter(countries) { country: CountryItem ->
            countryItemClicked(country)
        }

        binding.rvCountries.adapter = countryAdapter
        binding.rvCountries.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
    }

    private fun retrieveCountries() {
        RetrofitClient.instance.getCountries()
            .enqueue(object: Callback<ArrayList<CountryItem>> {
                override fun onResponse(call: Call<ArrayList<CountryItem>>, response: Response<ArrayList<CountryItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET COUNTRY ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            Toast.makeText(this@MainActivity, "There is no country data to display", Toast.LENGTH_LONG).show()
                        } else {
                            buildCountryList(list)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET COUNTRY ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<CountryItem>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET COUNTRY ITEMS FAIL", t.toString())
                }
            })
    }
}