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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ssn.studentapp.fragment.Details
import com.ssn.studentapp.R
import java.io.ByteArrayOutputStream
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"
private const val ARG_PARAM6 = "param6"
private const val Name = "name"
private const val Email = "email"
private const val Post = "post"
private const val Image = "image"
private const val Key = "key"
private const val Category = "category"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFaculity.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFaculity : Fragment(), OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: String? = null
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var button: Button
    private lateinit var button1: Button
    private var name: String? = null
    private var email: String? = null
    private var post: String? = null
    private var image: String? = null
    private var key: String? = null
    private var category: String? = null
    private var REQ: Int = 1
    private var bitmap: Bitmap? = null
    private val contentResolver: ContentResolver by lazy { requireActivity().contentResolver }
    private lateinit var pd: ProgressDialog
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uploadTask: UploadTask
    private lateinit var details: Details
    private lateinit var download: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        details = Details()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
            param6 = it.getString(ARG_PARAM6)
            name = it.getString(Name)
            email = it.getString(Email)
            post = it.getString(Post)
            image = it.getString(Image)
            key = it.getString(Key)
            category = it.getString(Category)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update_faculity, container, false)
        // Inflate the layout for this fragment
        imageView = view.findViewById(R.id.updateTeacherImage)
        editText = view.findViewById(R.id.updateTeacherName)
        editText1 = view.findViewById(R.id.updateTeacherEmail)
        editText2 = view.findViewById(R.id.updateTeacherPost)
        button = view.findViewById(R.id.updateTeacherBtn)
        button1 = view.findViewById(R.id.deleteTeacherBtn)
        pd = ProgressDialog(this@UpdateFaculity.requireContext())
        databaseReference = FirebaseDatabase.getInstance().reference.child("Teacher")
        storageReference = FirebaseStorage.getInstance().reference



        Glide.with(view).load(image).into(imageView)

        editText.setText(name)
        editText1.setText(email)
        editText2.setText(post)

        imageView.setOnClickListener { openGallery() }

        button.setOnClickListener(this)
        button1.setOnClickListener { deleteData() }


        return view
    }

    private fun deleteData() {
        databaseReference.child(category.toString()).child(key.toString()).removeValue().addOnCompleteListener {
            retrieveData()
            onFragment()
        }.addOnFailureListener {
            Toast.makeText(this@UpdateFaculity.requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
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

    private fun checkValidation() {
        name = editText.text.toString()
        email = editText1.text.toString()
        post = editText2.text.toString()
        if (name!!.isEmpty()) {
            editText.error = "Trống"
            editText.requestFocus()
        } else if (email!!.isEmpty()) {
            editText1.error = "Trống"
            editText1.requestFocus()
        } else if (post!!.isEmpty()) {
            editText2.error = "Trống"
            editText2.requestFocus()
        } else if (bitmap == null) {
            Toast.makeText(
                this@UpdateFaculity.requireContext(),
                "Please upload image",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            insertImages()
        }
    }

    private fun insertImages() {
        pd.setMessage("Uploading...")
        pd.show()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val finalImg = byteArrayOutputStream.toByteArray()
        val filPath: StorageReference = storageReference.child("Teacher")
            .child(finalImg.toString() + "jpg")
        uploadTask = filPath.putBytes(finalImg)

        // Add a success listener to the upload task
        uploadTask.addOnSuccessListener {
            filPath.downloadUrl.addOnSuccessListener { uri ->
                download = uri.toString()
                insertData()
                onFragment()
            }.addOnFailureListener { exception ->
                pd.dismiss()
                Toast.makeText(
                    this@UpdateFaculity.requireContext(),
                    "Failed to retrieve download URL: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener { exception ->
            pd.dismiss()
            Toast.makeText(
                this@UpdateFaculity.requireContext(),
                "Failed to upload image: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun insertData() {
        val hp: HashMap<String, Any> = HashMap()
        hp[Name] = name!!
        hp[Email] = email!!
        hp[Post] = post!!
        hp[Image] = image!!
        hp[Key] = key!!
        hp[Category] = category!!

        databaseReference.child(category.toString()).child(key.toString()).updateChildren(hp)
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
                            val key = teacherData.key
                            val category = category


                            Log.d("Teacher", "Name: $name")
                            Log.d("Teacher", "Email: $email")
                            Log.d("Teacher", "Post: $post")
                            Log.d("Teacher", "Image: $image")
                            Log.d("Teacher", "Key: $key")
                            Log.d("Teacher", "Category: $category")

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

    companion object {
        @JvmStatic
        fun newInstance(
            param1: String,
            param2: String,
            param3: String,
            param4: String,
            param5: String,
            param6: String,
        ) = UpdateFaculity().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
                putString(ARG_PARAM3, param3)
                putString(ARG_PARAM4, param4)
                putString(ARG_PARAM5, param5)
                putString(ARG_PARAM6, param6)
                putString(Name, param1)
                putString(Email, param2)
                putString(Post, param3)
                putString(Image, param4)
                putString(Key, param5)
                putString(Category, param6)
            }
        }
    }

    override fun onClick(p0: View?) {
        checkValidation()
    }

    private fun onFragment() {
        val fragmentManager: FragmentManager =
            requireActivity().supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, details)
        fragmentTransaction.commit()
        pd.dismiss()
    }
}