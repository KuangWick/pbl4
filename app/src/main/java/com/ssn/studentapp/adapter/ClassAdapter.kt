package com.ssn.studentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.models.ClassModel

class ClassAdapter(private val data: List<ClassModel>) :
    RecyclerView.Adapter<ClassAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classModel = data[position]
        holder.bind(classModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var maLop: TextView = itemView.findViewById(R.id.malop_tv)
        private var tenLop: TextView = itemView.findViewById(R.id.tenlop_tv)
        private var maKhoa: TextView = itemView.findViewById(R.id.makhoa_tv)

        fun bind(item: ClassModel) {
            maLop.text = item.MaLop
            tenLop.text = item.TenLop
            maKhoa.text = item.MaKhoa
        }

    }
}