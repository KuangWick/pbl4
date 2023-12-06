package com.ssn.studentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.models.SchoolModel

class SchoolAdapter(private val data: List<SchoolModel>) :
    RecyclerView.Adapter<SchoolAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.school_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schoolModel = data[position]
        holder.bind(schoolModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView = itemView.findViewById(R.id.school_Name)
        private var code: TextView = itemView.findViewById(R.id.school_code)
        private var address: TextView = itemView.findViewById(R.id.school_address)
        private var phone: TextView = itemView.findViewById(R.id.school_phone)
        private var email: TextView = itemView.findViewById(R.id.school_Email)

        fun bind(item: SchoolModel) {
            name.text = item.name
            code.text = item.code
            address.text = item.address
            phone.text = item.phone
            email.text = item.email
        }
    }
}