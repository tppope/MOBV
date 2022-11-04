package sk.stu.fei.mobv.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.ui.adapter.FirmItemAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<Firm>?) {
    val adapter = recyclerView.adapter as FirmItemAdapter
    adapter.submitList(listData)
}