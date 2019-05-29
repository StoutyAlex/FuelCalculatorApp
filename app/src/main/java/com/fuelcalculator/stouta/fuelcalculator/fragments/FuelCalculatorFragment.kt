package com.fuelcalculator.stouta.fuelcalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.fuelcalculator.stouta.fuelcalculator.CarModel

import com.fuelcalculator.stouta.fuelcalculator.R
import com.fuelcalculator.stouta.fuelcalculator.VolleySingleton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fuel_calculator.*
import kotlinx.android.synthetic.main.fragment_fuel_calculator.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 */
class FuelCalculatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_fuel_calculator, container, false)

        view.submit_button_car.setOnClickListener { getCarData() }

        return view
    }

    fun getCarData() {
        val url = "https://v8wc4obvre.execute-api.eu-west-1.amazonaws.com/default/fuel-calculator/car"
        testView.text = ""

        // Post parameters
        // Form fields and values
        val params = HashMap<String, String>()
        params["registration"] = "SA17VLR"
        val jsonObject = JSONObject(params)

        // Volley post request with parameters
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->

                val gson = Gson()
                val clientResponseModel = gson.fromJson(response.toString(), CarModel::class.java)

                try {
                    testView.text = "Response: ${clientResponseModel.clientData.combined} ${response.toString()}"
                } catch (e: Exception) {
                    testView.text = "Exception: $e"
                }

            }, Response.ErrorListener {
                // Error in request
                testView.text = "Volley error: $it"
            })


        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        VolleySingleton.getInstance(context!!).addToRequestQueue(request)
    }

}
