package com.ssn.studentapp.firebase.notice

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import com.ssn.studentapp.R.layout
import com.ssn.studentapp.fragment.navHome
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [UploadNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class UploadNotice : Fragment(), OnClickListener,
    OnCompleteListener<TaskSnapshot> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var cardView: CardView
    private var REQ: Int = 1
    private lateinit var bitmap: Bitmap
    private val contentResolver: ContentResolver by lazy { requireActivity().contentResolver }
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var download: String = ""
    private lateinit var pd: ProgressDialog
    private lateinit var uploadTask: UploadTask
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
        val view = inflater.inflate(layout.fragment_upload_notice, container, false)
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference
        pd = ProgressDialog(this@UploadNotice.requireContext())
        cardView = view.findViewById(R.id.addImage)
        imageView = view.findViewById(R.id.noticeImageView)
        editText = view.findViewById(R.id.noticeTitle)
        button = view.findViewById(R.id.uploadNoticeBtn)
        navHome = navHome()


        cardView.setOnClickListener {
            openGallery()
        }
        button.setOnClickListener(this)


        // Inflate the layout for this fragment
        return view
    }


    private fun uploadImage() {
        pd.setMessage("Uploading...")
        pd.show()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(JPEG, 50, byteArrayOutputStream)
        val finalImg = byteArrayOutputStream.toByteArray()
        val filPath: StorageReference = storageReference.child("Notice")
            .child(finalImg.toString() + "jpg")
        uploadTask = filPath.putBytes(finalImg)
        uploadTask.addOnCompleteListener {
            filPath.downloadUrl.addOnSuccessListener{ uri ->
                download = uri.toString()
                uploadData()
                onFragment()
            }.addOnFailureListener { exception ->
                pd.dismiss()
                // Upload failed.
                Toast.makeText(
                    this@UploadNotice.requireContext(),
                    "Failed to retrieve download URL: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }.addOnFailureListener { exception ->

                pd.dismiss()
                // Upload failed.
                Toast.makeText(
                    this@UploadNotice.requireContext(),
                    "Failed to upload image: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadData() {
        val uniqueKey: String? = databaseReference.child("Notice").push().key
        val title: String = editText.text.toString()
        val calForDate: Calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("dd-MM-yy")
        val date: String? = currentDate.format(calForDate.time)

        val calForTime: Calendar = Calendar.getInstance()
        val currentTime = SimpleDateFormat("hh:mm a")
        val time: String? = currentTime.format(calForTime.time)

        val notice = NoticeData(title, download, date, time, uniqueKey)
        databaseReference.child("Notice").child(uniqueKey.toString()).setValue(notice)
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
        databaseReference.child("Notice")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseData", dataSnapshot.toString())
                    for (noticeSnapshot in dataSnapshot.children) {
                        val notice = noticeSnapshot.getValue(NoticeData::class.java)

                        if (notice != null) {
                            val title = notice.title
                            val downloadUrl = notice.download
                            val date = notice.date
                            val time = notice.time

                            Log.d("Notice", "Title: $title")
                            Log.d("Notice", "Download URL: $downloadUrl")
                            Log.d("Notice", "Date: $date")
                            Log.d("Notice", "Time: $time")

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
    private fun onFragment() {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, navHome)
        fragmentTransaction.commit()
        pd.dismiss()
    }


    private fun openGallery() {
        val pickImage = Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage, REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            try {
                bitmap = Media.getBitmap(contentResolver, uri)
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
         * @return A new instance of fragment uploadNotice.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadNotice().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Trống"
            editText.requestFocus()
        } else {
            uploadImage()
        }
    }

    override fun onComplete(p0: Task<TaskSnapshot>) {
        if (p0.isSuccessful) {
            pd.dismiss()
            uploadImage()
            onFragment()
        } else {
            pd.dismiss()
            // Upload failed.
            Toast.makeText(
                this@UploadNotice.requireContext(),
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}