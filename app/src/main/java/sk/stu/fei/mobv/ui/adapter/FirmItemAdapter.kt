package sk.stu.fei.mobv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.databinding.FirmListItemBinding
import sk.stu.fei.mobv.domain.Firm

class FirmItemAdapter(
    private val firmEventListener: FirmEventListener
) : ListAdapter<Firm, FirmItemAdapter.FirmItemViewHolder>(DiffCallback) {
    class FirmItemViewHolder(var binding: FirmListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(firmEventListener: FirmEventListener, firm: Firm) {
            binding.firm = firm
            binding.firmEventListener = firmEventListener
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Firm>() {
        override fun areItemsTheSame(oldItem: Firm, newItem: Firm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Firm, newItem: Firm): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirmItemViewHolder {
        return FirmItemViewHolder(
            FirmListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FirmItemViewHolder, position: Int) {
        val firm: Firm = getItem(position)
        holder.bind(firmEventListener, firm)
    }

}

class FirmEventListener(val clickListener: (ownerName: String, firmId: Long) -> Unit) {
    fun onClick(ownerName: String, firmId: Long) = clickListener(ownerName, firmId)
}