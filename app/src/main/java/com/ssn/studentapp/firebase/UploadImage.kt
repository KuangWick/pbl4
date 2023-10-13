package com.ssn.studentapp.firebase

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
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.cardview.widget.CardView
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
import com.ssn.studentapp.R
import com.ssn.studentapp.navHome
import java.io.ByteArrayOutputStream
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [UploadImage.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadImage : Fragment(), CustomerSpinner.OnSpinnerEventsListener,
    OnCompleteListener<TaskSnapshot>, OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spinner: CustomerSpinner
    private lateinit var cardView: CardView
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var category: String
    private var REQ: Int = 1
    private val contentResolver: ContentResolver by lazy { requireActivity().contentResolver }
    private lateinit var bitmap: Bitmap
    private lateinit var pd: ProgressDialog
    private lateinit var uploadTask: UploadTask
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private var download: String = ""
    private lateinit var navHome: navHome

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
        val view = inflater.inflate(R.layout.fragment_upload_image, container, false)
        // Inflate the layout for this fragment
        storageReference = FirebaseStorage.getInstance().reference.child("gallery")
        databaseReference = FirebaseDatabase.getInstance().reference.child("gallery")
        cardView = view.findViewById(R.id.addGalleryImage)
        spinner = view.findViewById(R.id.image_category)
        button = view.findViewById(R.id.uploadImageBtn)
        imageView = view.findViewById(R.id.galleryImageView)
        pd = ProgressDialog(requireContext())
        navHome = navHome()


        cardView.setOnClickListener {
            openGallery()
        }
        button.setOnClickListener(this)
        val items = arrayOf("Chọn danh mục", "Cuộc hội thoại", "Ngày Quốc Khánh", "Sự kiện khác")

        val aDapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
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
                TODO("Not yet implemented")
            }

        }

        return view
    }

    private fun uploadImage() {
        pd.setMessage("Uploading...")
        pd.show()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val finalImg = byteArrayOutputStream.toByteArray()
        val filPath: StorageReference = storageReference.child(finalImg.toString() + "jpg")
        uploadTask = filPath.putBytes(finalImg)
        uploadTask.addOnCompleteListener(this)
    }

    private fun openGallery() {
        val pickImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage, REQ)
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
         * @return A new instance of fragment UploadImage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadImage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPopupWindowOpened(spinner: Spinner?) {
        spinner?.background = resources.getDrawable(R.drawable.bg_spinner)

    }

    override fun onPopupWindowClosed(spinner: Spinner?) {
        spinner?.background = resources.getDrawable(R.drawable.bg_spinner_up)
    }

    override fun onComplete(p0: Task<TaskSnapshot>) {
        if (p0.isSuccessful) {
            uploadTask.continueWith { task ->
                if (task.isSuccessful) {
                    storageReference.downloadUrl.continueWith { task1 ->
                        if (task1.isSuccessful) {
                            download = task.result.toString()
                            pd.dismiss()
                        } else {
                            // Download URL fetch failed.
                            Toast.makeText(
                                this@UploadImage.requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                            pd.dismiss()
                        }
                    }
                } else {
                    // Upload failed.
                    Toast.makeText(
                        this@UploadImage.requireContext(),
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                    pd.dismiss()
                }
            }
            upLoadData()
            onFragment()
        } else {
            pd.dismiss()
            // Upload failed.
            Toast.makeText(
                this@UploadImage.requireContext(),
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun upLoadData() {
        val uniqueKey1: String? = databaseReference.child(category).push().key
        databaseReference.child(uniqueKey1.toString()).setValue(download).addOnSuccessListener {
            pd.dismiss()
            retrieveData()
        }.addOnFailureListener { exception ->
            pd.dismiss()
            Toast.makeText(
                requireContext(),
                "Failed to upload gallery: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun retrieveData() {
        databaseReference.child("gallery")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseData", dataSnapshot.toString())
                    for (noticeSnapshot in dataSnapshot.children) {
                        val notice = noticeSnapshot.getValue(NoticeData::class.java)

                        if (notice != null) {
                            val uniquekey1 = notice.key

                            Log.d("gallery", "Title: $uniquekey1")

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


    override fun onClick(p0: View?) {
        if (category.equals("Chọn danh mục")) {
            Toast.makeText(
                this@UploadImage.requireContext(),
                "Vui lòng chọn danh mục ",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            uploadImage()
        }
    }
}