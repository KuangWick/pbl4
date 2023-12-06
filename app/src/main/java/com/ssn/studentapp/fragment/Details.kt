package com.ssn.studentapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ssn.studentapp.R
import com.ssn.studentapp.R.layout
import com.ssn.studentapp.firebase.TeacherAdapter
import com.ssn.studentapp.firebase.TeacherData
import com.ssn.studentapp.firebase.UploadFaculity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Details : Fragment(), OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var uploadFaculity: UploadFaculity
    private lateinit var csDepartment: RecyclerView
    private lateinit var dtDepartment: RecyclerView
    private lateinit var xdDepartment: RecyclerView
    private lateinit var qlDepartment: RecyclerView
    private lateinit var csNoData: LinearLayout
    private lateinit var dtNoData: LinearLayout
    private lateinit var xdNoData: LinearLayout
    private lateinit var qlNoData: LinearLayout
    private lateinit var list1: List<TeacherData>
    private lateinit var list2: List<TeacherData>
    private lateinit var list3: List<TeacherData>
    private lateinit var list4: List<TeacherData>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadFaculity = UploadFaculity()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_details, container, false)
        // Inflate the layout for this fragment

        csDepartment = view.findViewById(R.id.csDepartment)
        dtDepartment = view.findViewById(R.id.dtDepartment)
        xdDepartment = view.findViewById(R.id.xdDepartment)
        qlDepartment = view.findViewById(R.id.qlDepartment)


        csNoData = view.findViewById(R.id.csNoData)
        dtNoData = view.findViewById(R.id.dtNoData)
        xdNoData = view.findViewById(R.id.xdNoData)
        qlNoData = view.findViewById(R.id.qlNoData)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Teacher")


        csDepartments()
        dtDepartments()
        xdDepartments()
        qlDepartments()


        fab = view.findViewById(R.id.fab)

        fab.setOnClickListener(this)

        return view
    }

    private fun qlDepartments() {
        val context = this@Details.requireContext()
        databaseReference = databaseReference.child("Khoa Quản lý Dự Án")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list4 = ArrayList()
                if (!snapshot.exists()) {
                    qlNoData.visibility = View.VISIBLE
                    qlDepartment.visibility = View.GONE
                } else {
                    qlNoData.visibility = View.GONE
                    qlDepartment.visibility = View.VISIBLE

                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val data: TeacherData? = dataSnapshot.getValue(TeacherData::class.java)
                        data?.let {
                            (list4 as ArrayList<TeacherData>).add(it)
                        }
                    }
                    qlDepartment.setHasFixedSize(true)
                    qlDepartment.layoutManager = LinearLayoutManager(context)
                    adapter = TeacherAdapter(list4, context, "Khoa Quản lý Dự Án")
                    qlDepartment.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Details.requireContext(), error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun xdDepartments() {
        val context = this@Details.requireContext()
        databaseReference = databaseReference.child("Khoa Xây Dựng")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list3 = ArrayList()
                if (!snapshot.exists()) {
                    xdNoData.visibility = View.VISIBLE
                    xdDepartment.visibility = View.GONE
                } else {
                    xdNoData.visibility = View.GONE
                    xdDepartment.visibility = View.VISIBLE

                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val data: TeacherData? = dataSnapshot.getValue(TeacherData::class.java)
                        data?.let {
                            (list3 as ArrayList<TeacherData>).add(it)
                        }
                    }

                    xdDepartment.setHasFixedSize(true)
                    xdDepartment.layoutManager = LinearLayoutManager(context)
                    adapter = TeacherAdapter(list3, context, "Khoa Xây Dựng")
                    xdDepartment.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Details.requireContext(), error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun dtDepartments() {
        val context = this@Details.requireContext()
        databaseReference = databaseReference.child("Khoa Điện Tử Viên Thông")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list2 = ArrayList()
                if (!snapshot.exists()) {
                    dtNoData.visibility = View.VISIBLE
                    dtDepartment.visibility = View.GONE
                } else {
                    dtNoData.visibility = View.GONE
                    dtDepartment.visibility = View.VISIBLE

                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val data: TeacherData? = dataSnapshot.getValue(TeacherData::class.java)
                        data?.let {
                            (list2 as ArrayList<TeacherData>).add(it)
                        }
                    }
                    dtDepartment.setHasFixedSize(true)
                    dtDepartment.layoutManager = LinearLayoutManager(context)
                    adapter = TeacherAdapter(list2, context, "Khoa Điện Tử Viên Thông")
                    dtDepartment.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Details.requireContext(), error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun csDepartments() {
        val context = this@Details.requireContext()
        databaseReference = databaseReference.child("Khoa Công Nghệ Thông Tin")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list1 = ArrayList()
                if (!snapshot.exists()) {
                    csNoData.visibility = View.VISIBLE
                    csDepartment.visibility = View.GONE
                } else {
                    csNoData.visibility = View.GONE
                    csDepartment.visibility = View.VISIBLE
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        val data: TeacherData? = dataSnapshot.getValue(TeacherData::class.java)
                        data?.let {
                            (list1 as ArrayList<TeacherData>).add(it)
                        }
                    }
                    csDepartment.setHasFixedSize(true)
                    csDepartment.layoutManager = LinearLayoutManager(context)
                    adapter = TeacherAdapter(list1, context, "Khoa Công Nghệ Thông Tin")
                    csDepartment.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Details.requireContext(), error.message, Toast.LENGTH_SHORT)
                    .show()
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
         * @return A new instance of fragment Details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Details().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onClick(p0: View?) {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, uploadFaculity)
        fragmentTransaction.commit()
    }

}