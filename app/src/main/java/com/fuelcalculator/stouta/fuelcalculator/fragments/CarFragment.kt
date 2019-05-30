package com.fuelcalculator.stouta.fuelcalculator.fragments

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.fuelcalculator.stouta.fuelcalculator.CarModel

import com.fuelcalculator.stouta.fuelcalculator.R
import com.fuelcalculator.stouta.fuelcalculator.VolleySingleton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_car.*
import kotlinx.android.synthetic.main.fragment_car.view.*
import kotlinx.android.synthetic.main.new_car_dialog.*
import org.json.JSONObject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 */
class CarFragment : Fragment() {

    internal lateinit var dialogBox : Dialog
    internal lateinit var dialogConfirm : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_car, container, false)

        view.submit_button_get_car.setOnClickListener {
            showDialog()
        }

        return view
    }

    fun showDialog() {
        dialogBox = Dialog(context)
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBox.setContentView(R.layout.new_car_dialog)
        dialogBox.setTitle("New Car")

        dialogBox.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        dialogConfirm = dialogBox.findViewById<View>(R.id.dialog_confirm_button) as Button
        dialogConfirm.setOnClickListener {
            Toast.makeText(context, "Getting car", Toast.LENGTH_LONG)
            findCar(dialogBox.text_car_registration.text.toString())
//            dialogBox.cancel()
        }
        dialogBox.show()
    }

    fun findCar(registration: String) {

        val url = "https://v8wc4obvre.execute-api.eu-west-1.amazonaws.com/default/fuel-calculator/car"
        var textBox = dialogBox.findViewById<View>(R.id.car_test_text) as TextView


        // Post parameters
        // Form fields and values
        val params = HashMap<String, String>()
        params["registration"] = registration
        val jsonObject = JSONObject(params)

        // Volley post request with parameters
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->

                val gson = Gson()
                val clientResponseModel = gson.fromJson(response.toString(), CarModel::class.java)

                try {
                    textBox.text = "Response: ${response}"
                } catch (e: Exception) {
                    textBox.text = "Exception: $e"
                }

            }, Response.ErrorListener {
//                 Error in request
                textBox.text = "Volley error: $it"
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
