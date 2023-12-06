package com.ssn.studentapp.fragment

import android.R.layout
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import com.ssn.studentapp.R
import com.ssn.studentapp.firebase.CustomerSpinner
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Language.newInstance] factory method to
 * create an instance of this fragment.
 */
class Language : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var spinner: CustomerSpinner
    private lateinit var student: Student

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
        val view = inflater.inflate(R.layout.fragment_language, container, false)
        student = Student()
        spinner = view.findViewById(R.id.spinner)
        val items = arrayOf("Select language", "English", "tiếng việt")
        val aDapter = ArrayAdapter(
            requireContext(),
            layout.simple_spinner_dropdown_item,
            items
        )
        spinner.adapter = aDapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val seletedLang = parent?.getItemAtPosition(position).toString()
                if (seletedLang.equals("English")){
                    setLocal(container!!.context, "en")
                    onFragment()

                }else if (seletedLang.equals("tiếng việt")){
                    setLocal(container!!.context, "hi")
                    onFragment()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    fun setLocal(context: Context, langCode: String){
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config,resources.displayMetrics)

    }

    private fun onFragment() {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, student)
        fragmentTransaction.commit()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Language.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Language().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}