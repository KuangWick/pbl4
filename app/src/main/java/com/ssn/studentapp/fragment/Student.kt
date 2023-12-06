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
import com.ssn.studentapp.R.layout
import com.ssn.studentapp.adapter.StudentAdapter
import com.ssn.studentapp.api.RetrofitClient
import com.ssn.studentapp.data.StudentResult
import com.ssn.studentapp.models.StudentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Student : Fragment() {
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
        val view = inflater.inflate(layout.fragment_student, container, false)
        getDataStudent()
        return view
    }

    private fun getDataStudent() {
        val apiService = RetrofitClient.getInstance().getStringApi()
        val call = apiService.getAllStudent()
        call.enqueue(object : Callback<StudentResult> {
            override fun onResponse(call: Call<StudentResult>, response: Response<StudentResult>) {
                if (response.isSuccessful) {
                    try {
                        val data = response.body()?.AllStudent
                        Log.e("API Response", "Empty or null: $data")
                        if (data != null) {
                            val stringData: List<StudentModel> = data
                            val recyclerView: RecyclerView = view!!.findViewById(R.id.student_rcclv)
                            val studentAdapter = StudentAdapter(stringData)
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.adapter = studentAdapter
                        } else {
                            Log.e("API Response", "Empty or null studentResult")
                        }
                    } catch (e: Exception) {
                        Log.e("JSON Parsing Error", "Error parsing JSON response: ${e.message}")
                    }
                } else {
                    Log.e("API Response", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StudentResult>, t: Throwable) {
                Log.e("API Failure", "API call failed: ${t.message}")
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
         * @return A new instance of fragment Student.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Student().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}