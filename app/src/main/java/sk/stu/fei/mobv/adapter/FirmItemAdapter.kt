package sk.stu.fei.mobv.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.FirmListFragmentDirections
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.model.Firm

class FirmItemAdapter(
    private val context: Context,
    private val firmList: List<Firm>

) : RecyclerView.Adapter<FirmItemAdapter.FirmItemViewHolder>() {
    class FirmItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val firmItem: RelativeLayout = view.findViewById(R.id.firm_item)
        val firmTitle: TextView = view.findViewById(R.id.firm_title)
        val firmDescription: TextView = view.findViewById(R.id.firm_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirmItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.firm_list_item, parent, false)
        return FirmItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FirmItemViewHolder, position: Int) {
        val firm: Firm = firmList[position]
        holder.firmTitle.text = firm.tags?.firmName.toString()
        holder.firmDescription.text = firm.tags?.ownerName

        holder.firmItem.setOnClickListener{
            val action = FirmListFragmentDirections.actionFirmListFragmentToFirmFragment(
                ownerName = firm.tags?.ownerName.toString(),
                firmName = firm.tags?.firmName.toString(),
                latitude = firm.latitude.toString(),
                longitude = firm.longitude.toString(),
                phoneNumber = firm.tags?.phoneNumber.toString()
            )
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = firmList.size

}