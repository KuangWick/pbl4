package com.ssn.studentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.models.StudentModel

class StudentAdapter(private val data: List<StudentModel>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentModel = data[position]
        holder.bind(studentModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var Masv: TextView = itemView.findViewById(R.id.maSv)
        private var HoTenSv: TextView = itemView.findViewById(R.id.hoTenSv)
        private var MaLop: TextView = itemView.findViewById(R.id.maLop)
        private var School_tv: TextView = itemView.findViewById(R.id.school)
        private var DiaChi: TextView = itemView.findViewById(R.id.diaChi)
        private var NgaySinh: TextView = itemView.findViewById(R.id.ngaySinh)
        private var GioiTinh: TextView = itemView.findViewById(R.id.gioiTinh)
        private var Email_tv: TextView = itemView.findViewById(R.id.email)

        fun bind(item: StudentModel) {
            Masv.text = item.MaSv
            HoTenSv.text = item.HoTenSv
            MaLop.text = item.MaLop
            School_tv.text = item.School
            DiaChi.text = item.DiaChi
            NgaySinh.text = item.NgaySinh
            GioiTinh.text = item.Gioitinh
            Email_tv.text = item.Email
        }
    }

}