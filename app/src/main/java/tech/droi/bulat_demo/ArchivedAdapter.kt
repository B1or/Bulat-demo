package tech.droi.bulat_demo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import tech.droi.bulat_demo.databinding.ItemArchivedBinding


class ArchivedAdapter(private val dataSource: ArrayList<GridArchived>, private val month: Int?) : BaseAdapter() {

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): GridArchived {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val binding = ItemArchivedBinding.inflate(inflater)
        val data = getItem(position)
        binding.tvNumberItemArchived.text = "%02d".format(data.dt + if (month == null) 1 else 0) + (if (month == null) ":00" else "." + "%02d".format(month))
        binding.tvT0ItemArchived.text = if (data.t0 != null) String.format(MainActivity.FORMAT3, data.t0) else ""
        binding.tvT1ItemArchived.text = if (data.t1 != null) String.format(MainActivity.FORMAT3, data.t1) else ""
        binding.tvT2ItemArchived.text = if (data.t2 != null) String.format(MainActivity.FORMAT3, data.t2) else ""
        binding.tvT3ItemArchived.text = if (data.t3 != null) String.format(MainActivity.FORMAT3, data.t3) else ""
        binding.tvT4ItemArchived.text = if (data.t4 != null) String.format(MainActivity.FORMAT3, data.t4) else ""
        return binding.root
    }
}
