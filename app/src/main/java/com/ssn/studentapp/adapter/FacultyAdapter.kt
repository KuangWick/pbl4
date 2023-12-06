import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssn.studentapp.R
import com.ssn.studentapp.models.FacultyModel

class FacultyAdapter(private val data: List<FacultyModel>) :
    RecyclerView.Adapter<FacultyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faculty_item_layout1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facultyModel = data[position]
        holder.bind(facultyModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var maKhoa: TextView = itemView.findViewById(R.id.maKhoa)
        private var tenKhoa: TextView = itemView.findViewById(R.id.tenKhoa)
        fun bind(item: FacultyModel) {
            maKhoa.text = item.maKhoa
            tenKhoa.text = item.tenKhoa
        }
    }
}





