package com.ssn.studentapp.firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask.TaskSnapshot
import com.ssn.studentapp.R
import com.ssn.studentapp.fragment.navHome
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UploadEbook.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadEbook : Fragment(), OnClickListener, OnSuccessListener<TaskSnapshot>,
    OnFailureListener, OnCompleteListener<Void> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var cardView: CardView
    private var REQ: Int = 1
    private lateinit var uri: Uri
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var pd: ProgressDialog
    private lateinit var navHome: navHome
    private lateinit var textView: TextView
    private lateinit var pdfName: String
    private lateinit var title: String

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

        val view = inflater.inflate(R.layout.fragment_upload_ebook, container, false)
        // Inflate the layout for this fragment
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference
        pd = ProgressDialog(this.requireContext())
        navHome = navHome()

        cardView = view.findViewById(R.id.addPdf)
        editText = view.findViewById(R.id.pdfTitle)
        button = view.findViewById(R.id.uploadPdfBtn)
        textView = view.findViewById(R.id.pdfTextView)

        cardView.setOnClickListener {
            openGallery()
        }
        button.setOnClickListener(this)


        return view
    }


    private fun openGallery() {
        val pickPdf = Intent()
        pickPdf.type = "application/pdf"
        pickPdf.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(pickPdf, "Select PDF file"), REQ)
    }

    private fun onFragment() {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, navHome)
        fragmentTransaction.commit()
        pd.dismiss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ && resultCode == Activity.RESULT_OK) {
            uri = data?.data!!
            if (uri.toString().startsWith("content://")) {
                try {
                    val cursor = this@UploadEbook.requireContext().contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null
                    )
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            pdfName = it.getString(displayNameIndex)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else if (uri.toString().startsWith("file://")) {
                pdfName = File(uri.toString()).name
            }
            textView.text = pdfName
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UploadEbook.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadEbook().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        title = editText.text.toString()
        if (title.isEmpty()) {
            editText.error = "Trống"
            editText.requestFocus()
        } else if (uri == null) {
            Toast.makeText(
                this@UploadEbook.requireContext(),
                "Please upload PDF",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            uploadPdf()
        }
    }

    private fun uploadPdf() {
        pd.setTitle("Please wait...")
        pd.setMessage("Uploading PDF")
        pd.show()
        storageReference =
            storageReference.child("pdf/$pdfName-" + System.currentTimeMillis() + ".pdf")
        storageReference.putFile(uri).addOnSuccessListener(this).addOnFailureListener(this)

    }

    override fun onSuccess(p0: TaskSnapshot?) {
        val task: Task<Uri>? = p0?.storage?.downloadUrl
        while (task?.isComplete != true);
        val uri: Uri = task.result
        val filePath = uri.toString()
        uploadData(filePath)
    }

    private fun uploadData(filePath: String) {
        val uniqueKey = databaseReference.child("pdf").push().key
        val data = HashMap<String, Any>()
        data["pdfTitle"] = title
        data["pdfUrl"] = filePath

        databaseReference.child("pdf").child(uniqueKey.toString()).setValue(data)
            .addOnCompleteListener(this).addOnFailureListener {
                pd.dismiss()
                Toast.makeText(
                    this@UploadEbook.requireContext(),
                    "Failed to upload PDF",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onFailure(p0: java.lang.Exception) {
        pd.dismiss()
        Toast.makeText(
            this@UploadEbook.requireContext(),
            "Something went wrong",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onComplete(p0: Task<Void>) {
        pd.dismiss()
        Toast.makeText(
            this@UploadEbook.requireContext(),
            "PDF uploaded succeeded",
            Toast.LENGTH_SHORT
        ).show()
        editText.setText("")
        onFragment()
    }
}