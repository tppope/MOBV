package sk.stu.fei.mobv.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.ui.adapter.FirmItemAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<Firm>?) {
    val adapter = recyclerView.adapter as FirmItemAdapter
    adapter.submitList(listData)
}

@BindingAdapter("firmType")
fun chooseFirmTypIcon(imageView: ImageView, firmType: String) {
    imageView.setImageResource(
        when(firmType){
            "pub" -> R.drawable.ic_pub
            "bar" -> R.drawable.ic_bar
            else -> R.drawable.ic_nightclub
        }
    )
}