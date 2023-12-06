package com.ssn.studentapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.adapter.SchoolAdapter
import com.ssn.studentapp.api.RetrofitClient
import com.ssn.studentapp.data.SchoolResult
import com.ssn.studentapp.models.SchoolModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [School.newInstance] factory method to
 * create an instance of this fragment.
 */
class School : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_school, container, false)
        getData()
        return view
    }

    private fun getData() {
        val apiService = RetrofitClient.getInstance().getStringApi()
        val call = apiService.getAllSchool()
        call.enqueue(object : Callback<SchoolResult> {
            override fun onResponse(call: Call<SchoolResult>, response: Response<SchoolResult>) {
                if (response.isSuccessful) {
                    try {
                        val data: List<SchoolModel>? = response.body()?.AllSchool
                        if (data != null) {
                            val stringData: List<SchoolModel> = data
                            val recyclerView: RecyclerView = view!!.findViewById(R.id.school_rcclv)
                            val schoolAdapter = SchoolAdapter(stringData)
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.adapter = schoolAdapter
                        } else {
                            Log.e("API Response", "Empty or null schoolResult")
                        }
                    } catch (e: Exception) {
                        Log.e("JSON Parsing Error", "Error parsing JSON response: ${e.message}")

                    }
                } else {
                    Log.e("Api response", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SchoolResult>, t: Throwable) {
                Log.e("API Failure", "API call Failed: ${t.message}")
            }

        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment School.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            School().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}