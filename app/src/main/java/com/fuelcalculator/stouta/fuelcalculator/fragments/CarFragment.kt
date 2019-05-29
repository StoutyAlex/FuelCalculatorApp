package com.fuelcalculator.stouta.fuelcalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuelcalculator.stouta.fuelcalculator.MainActivity

import com.fuelcalculator.stouta.fuelcalculator.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 */
class CarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_car, container, false)
    }

//    companion object {
//        fun newInstance(): CarFragment {
//            return CarFragment()
//        }
//    }
}
