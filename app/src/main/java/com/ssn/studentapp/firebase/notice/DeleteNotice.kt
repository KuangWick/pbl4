package com.ssn.studentapp.firebase.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ssn.studentapp.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DeleteNotice : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var list: List<NoticeData>
    private lateinit var adapter: NoticeAdapter
    private lateinit var databaseReference: DatabaseReference


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
        val view = inflater.inflate(R.layout.fragment_delete_notice, container, false)
        recyclerView = view.findViewById(R.id.deleteNoticeRecycler)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Notice")
        getNotice()

        return view
    }

    private fun getNotice() {
        val context = requireContext()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<NoticeData>()
                for (snapShot in dataSnapshot.children) {
                    val notice: NoticeData? = snapShot.getValue(NoticeData::class.java)
                    notice?.let { list.add(it) }
                }
                adapter = NoticeAdapter(list, context)
                recyclerView.adapter = adapter
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment DeleteNotice.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeleteNotice().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}