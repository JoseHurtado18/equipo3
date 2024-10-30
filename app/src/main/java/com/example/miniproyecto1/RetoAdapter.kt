// RetoAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproyecto1.R

class RetoAdapter(
    private val retos: List<Reto>,
    private val onEditClick: (Reto) -> Unit,
    private val onDeleteClick: (Reto) -> Unit
) : RecyclerView.Adapter<RetoAdapter.RetoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reto, parent, false)
        return RetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetoViewHolder, position: Int) {
        val reto = retos[position]
        holder.bind(reto)
    }

    override fun getItemCount(): Int = retos.size

    inner class RetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcionTextView: TextView = itemView.findViewById(R.id.retoTextView)
        private val editButton: ImageView = itemView.findViewById(R.id.editImageView)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteImageView)

        fun bind(reto: Reto) {
            descripcionTextView.text = reto.descripcion

            // Manejar el clic en el botón de editar
            editButton.setOnClickListener {
                onEditClick(reto)
            }

            // Manejar el clic en el botón de eliminar
            deleteButton.setOnClickListener {
                onDeleteClick(reto)
            }
        }
    }
}