package com.ssn.studentapp.firebase.notice

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ssn.studentapp.R


class NoticeAdapter(
    private var list: List<NoticeData>, private val context: Context
) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val item: NoticeData = list[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var button: Button = itemView.findViewById(R.id.deleteNotice)
        private var textView: TextView = itemView.findViewById(R.id.deleteNoticeTitle)
        private var imageView: ImageView = itemView.findViewById(R.id.deleteNoticeImage)
        private var databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("Notice")

        init {
            button.setOnClickListener(this)
        }

        fun bind(noticeData: NoticeData, position: Int) {
            textView.text = noticeData.title

            button.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                builder.setMessage("Bạn có chắc chắn muốn xóa thông báo này?")
                builder.setCancelable(true)
                builder.setPositiveButton(
                    "Ok"
                ) { p0, p1 ->
                    databaseReference.child(noticeData.key.toString()).removeValue()
                        .addOnCompleteListener {
                            list = list.filterIndexed { index, _ -> index != position }
                            notifyItemRemoved(position)
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }


                }
                builder.setNegativeButton(
                    "Cancel"
                ) { p0, p1 ->
                    p0.cancel()
                }

                builder.show()
            }
            Glide.with(itemView).load(noticeData.download).into(imageView)
        }

        override fun onClick(view: View?) {
            // Handle button click here if needed
        }
    }
}

class NoticeAdapter1(private var list: List<NoticeData>, private val context: Context) :
    RecyclerView.Adapter<NoticeAdapter1.NoticeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.feed_item_layout, parent, false)
        return NoticeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val item: NoticeData = list[position]
        holder.bind(item, position)
    }

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var textView: TextView = itemView.findViewById(R.id.homeNoticeName)
        private var textView1: TextView = itemView.findViewById(R.id.homeNoticeTitle)
        private var imageView: ImageView = itemView.findViewById(R.id.homeNoticeImage)


        fun bind(noticeData: NoticeData, position: Int) {
            textView.text
            textView1.text = noticeData.title
            Glide.with(itemView).load(noticeData.download).into(imageView)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

}