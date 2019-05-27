package com.fuelcalculator.stouta.fuelcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var jsonString = """{ "success": true, "data": {
                                    "duration": "126.85",
                                    "distance": "121.13",
                                    "fuelTankCapacity": 45,
                                    "tanksUsed": "0.22",
                                    "litresUsed": "9.9",
                                    "start": "Salford M50 1AZ, UK",
                                    "end": "Church St, Orston, Nottingham NG13 9NW, UK",
                                    "averageSpeed": "57.30",
                                    "journeyCost": "12.85",
                                    "mpg": 55.4 }}"""

//        val gson = Gson()
//        val clientResponseModel = gson.fromJson(jsonString, FuelCostModel::class.java)
//
//        Log.i("AABBCC", "here it is")
//        Log.i("sccess",clientResponseModel.getSuccess());
//        Log.i("distance",""+clientResponseModel.getClientData().distance);

        submit_button.setOnClickListener {this.getCarData()}
        submit_button_car.setOnClickListener {this.getCarData()}
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
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }
}
