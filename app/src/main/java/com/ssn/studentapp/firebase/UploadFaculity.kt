package com.ssn.studentapp.firebase

import android.R.layout
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot
import com.ssn.studentapp.fragment.Details
import com.ssn.studentapp.R
import com.ssn.studentapp.firebase.CustomerSpinner.OnSpinnerEventsListener
import java.io.ByteArrayOutputStream
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UploadFaculity.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadFaculity : Fragment(), OnSpinnerEventsListener, OnClickListener,
    OnCompleteListener<TaskSnapshot> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var REQ: Int = 1
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var spinner: CustomerSpinner
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var button: Button
    private lateinit var bitmap: Bitmap
    private lateinit var category: String
    private val contentResolver: ContentResolver by lazy { requireActivity().contentResolver }
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var post: String
    private var download: String = ""
    private lateinit var pd: ProgressDialog
    private lateinit var uploadTask: UploadTask
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var details: Details


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        details = Details()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_faculity, container, false)
        // Inflate the layout for this fragment
        imageView = view.findViewById(R.id.addTeacherImage)
        editText = view.findViewById(R.id.addTeacherName)
        editText1 = view.findViewById(R.id.addTeacherEmail)
        editText2 = view.findViewById(R.id.addTeacherPost)
        spinner = view.findViewById(R.id.addTeacherCategory)
        button = view.findViewById(R.id.uploadTeacherBtn)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Teacher")
        storageReference = FirebaseStorage.getInstance().reference
        pd = ProgressDialog(this@UploadFaculity.requireContext())

        val items = arrayOf(
            "Chọn Khoa",
            "Khoa Công Nghệ Thông Tin",
            "Khoa Cơ Khí",
            "Khao Kiến Trúc",
            "Khoa Điện Tử Viên Thông",
            "Khoa Vật Lý",
            "Khoa Hoá",
            "Khoa Xây Dựng",
            "Khoa Quản lý Dự Án"
        )

        val aDapter = ArrayAdapter(
            requireContext(),
            layout.simple_spinner_dropdown_item,
            items
        )
        spinner.adapter = aDapter
        spinner.setSpinnerEventsListener(this)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


        imageView.setOnClickListener { openGallery() }

        button.setOnClickListener(this)

        return view
    }

    private fun openGallery() {
        val pickImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage, REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            imageView.setImageBitmap(bitmap)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UploadFaculity.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadFaculity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPopupWindowOpened(spinner: Spinner?) {
        spinner?.background = resources.getDrawable(R.drawable.bg_spinner_up)

    }

    override fun onPopupWindowClosed(spinner: Spinner?) {
        spinner?.background = resources.getDrawable(R.drawable.bg_spinner)
    }

    override fun onClick(p0: View?) {
        checkValidation()
    }

    private fun checkValidation() {
        name = editText.text.toString()
        email = editText1.text.toString()
        post = editText2.text.toString()
        if (name.isEmpty()) {
            editText.error = "Trống"
            editText.requestFocus()
        } else if (email.isEmpty()) {
            editText1.error = "Trống"
            editText1.requestFocus()
        } else if (post.isEmpty()) {
            editText2.error = "Trống"
            editText2.requestFocus()
        } else if (category == "Chọn Khoa") {
            Toast.makeText(
                this@UploadFaculity.requireContext(),
                " Please provide teacher category",
                Toast.LENGTH_SHORT
            ).show()
        } else if (bitmap == null) {
            Toast.makeText(
                this@UploadFaculity.requireContext(),
                "Please upload image",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            insertImages()
        }
    }


    private fun insertData() {
        val uniqueKey: String? = databaseReference.push().key
        val teacherData = TeacherData(name, email, post, download, uniqueKey.toString())
        databaseReference.child(category).child(uniqueKey.toString()).setValue(teacherData)
            .addOnSuccessListener {
                pd.dismiss()
                retrieveData()
            }
            .addOnFailureListener { exception ->
                pd.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Failed to upload notice: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun retrieveData() {
        databaseReference.child("Teacher")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseData", dataSnapshot.toString())
                    for (teacherSnapshot in dataSnapshot.children) {
                        val teacherData = teacherSnapshot.getValue(TeacherData::class.java)

                        if (teacherData != null) {
                            val name = teacherData.name
                            val email = teacherData.email
                            val post = teacherData.post
                            val image = teacherData.image


                            Log.d("Teacher", "Name: $name")
                            Log.d("Teacher", "Email: $email")
                            Log.d("Teacher", "Post: $post")
                            Log.d("Teacher", "Image: $image")

                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to read data: ${databaseError.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun insertImages() {
        pd.setMessage("Uploading...")
        pd.show()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val finalImg = byteArrayOutputStream.toByteArray()
        val filPath: StorageReference = storageReference.child("Teacher")
            .child(finalImg.toString() + "jpg")
        uploadTask = filPath.putBytes(finalImg)

        // Add a success listener to the upload task
        uploadTask.addOnSuccessListener { taskSnapshot ->
            filPath.downloadUrl.addOnSuccessListener { uri ->
                download = uri.toString()
                insertData()
                onFragment()
            }.addOnFailureListener { exception ->
                pd.dismiss()
                Toast.makeText(
                    this@UploadFaculity.requireContext(),
                    "Failed to retrieve download URL: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener { exception ->
            pd.dismiss()
            Toast.makeText(
                this@UploadFaculity.requireContext(),
                "Failed to upload image: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onComplete(p0: Task<TaskSnapshot>) {
        if (p0.isSuccessful) {
            pd.dismiss()
            insertImages()
            onFragment()
        } else {
            pd.dismiss()
            Toast.makeText(
                this@UploadFaculity.requireContext(),
                "Failed to upload image",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onFragment() {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, details)
        fragmentTransaction.commit()
        pd.dismiss()
    }
}