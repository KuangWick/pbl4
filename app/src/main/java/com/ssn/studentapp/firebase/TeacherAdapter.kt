package com.ssn.studentapp.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssn.studentapp.R


class TeacherAdapter(
    private var list: List<TeacherData>, private val context: Context, private val category: String
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.faculity_item_layout, parent, false)
        return TeacherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val item: TeacherData = list[position]
        holder.bind(item)
    }

    inner class TeacherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val nameTextView: TextView = itemView.findViewById(R.id.teacherName)
        private val emailTextView: TextView = itemView.findViewById(R.id.teacherEmail)
        private val postTextView: TextView = itemView.findViewById(R.id.teacherPost)
        private var imageView: ImageView = itemView.findViewById(R.id.teacherImage)
        private val updateButton: Button = itemView.findViewById(R.id.teacherUpdate)

        init {
            updateButton.setOnClickListener(this)
        }

        fun bind(teacherData: TeacherData) {
            nameTextView.text = teacherData.name
            emailTextView.text = teacherData.email
            postTextView.text = teacherData.post
            Glide.with(itemView).load(teacherData.image).into(imageView)
        }

        override fun onClick(p0: View?) {
            if (p0?.id == R.id.teacherUpdate) {
                val teacherData = list[adapterPosition]
                val updateFaculity = UpdateFaculity.newInstance(
                    teacherData.name.toString(),
                    teacherData.email.toString(),
                    teacherData.post.toString(),
                    teacherData.image.toString(),
                    teacherData.key.toString(),
                    category
                )
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, updateFaculity)
                fragmentTransaction.commit()

            }
        }


    }
}


