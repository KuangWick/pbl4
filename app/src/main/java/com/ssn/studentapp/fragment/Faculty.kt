package com.ssn.studentapp.fragment

import FacultyAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.api.RetrofitClient
import com.ssn.studentapp.data.FacultyResult
import com.ssn.studentapp.models.FacultyModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Faculty : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_faculity, container, false)
        fetchData()
        return view
    }

    private fun fetchData() {
        val apiService = RetrofitClient.getInstance().getStringApi()
        val call = apiService.getAllFaculty()
        call.enqueue(object : Callback<FacultyResult> {
            override fun onResponse(call: Call<FacultyResult>, response: Response<FacultyResult>) {
                if (response.isSuccessful) {
                    try {
                        val data: List<FacultyModel>? = response.body()?.AllFaculty
                        if (data != null) {
                            val stringData: List<FacultyModel> = data
                            val recyclerView: RecyclerView = view!!.findViewById(R.id.cNTT)
                            val facultyAdapter = FacultyAdapter(stringData)
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.adapter = facultyAdapter
                        } else {
                            Log.e("API Response", "Empty or null facultyResult")
                        }
                    } catch (e: Exception) {
                        Log.e("JSON Parsing Error", "Error parsing JSON response: ${e.message}")
                    }
                } else {
                    Log.e("API Response", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FacultyResult>, t: Throwable) {
                Log.e("API Failure", "API call failed: ${t.message}")
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Faculty().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}