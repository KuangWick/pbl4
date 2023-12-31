package com.ssn.studentapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.card.MaterialCardView
import com.ssn.studentapp.R
import com.ssn.studentapp.R.layout
import com.ssn.studentapp.firebase.UploadEbook
import com.ssn.studentapp.firebase.UploadFaculity
import com.ssn.studentapp.firebase.UploadImage
import com.ssn.studentapp.firebase.notice.DeleteNotice
import com.ssn.studentapp.firebase.notice.UploadNotice

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var materialCard1: MaterialCardView
    private lateinit var uploadNotice: UploadNotice
    private lateinit var materialCard2: MaterialCardView
    private lateinit var materialCard3: MaterialCardView
    private lateinit var materialCard4: MaterialCardView
    private lateinit var materialCard5: MaterialCardView
    private lateinit var uploadImage: UploadImage
    private lateinit var uploadEbook: UploadEbook
    private lateinit var uploadFaculity: UploadFaculity
    private lateinit var deleteNotice: DeleteNotice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadNotice = UploadNotice()
        uploadImage = UploadImage()
        uploadEbook = UploadEbook()
        uploadFaculity = UploadFaculity()
        deleteNotice = DeleteNotice()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        materialCard1 = view.findViewById(R.id.btnNotice)
        materialCard1.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, uploadNotice)
            fragmentTransaction.commit()
        }
        materialCard2 = view.findViewById(R.id.btnGalleryImage)
        materialCard2.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, uploadImage)
            fragmentTransaction.commit()
        }
        materialCard3 = view.findViewById(R.id.btnEbook)
        materialCard3.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, uploadEbook)
            fragmentTransaction.commit()
        }
        materialCard4 = view.findViewById(R.id.faculity)
        materialCard4.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, uploadFaculity)
            fragmentTransaction.commit()
        }
        materialCard5 = view.findViewById(R.id.btnDelete)
        materialCard5.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.container, deleteNotice)
            fragmentTransaction.commit()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Home().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}