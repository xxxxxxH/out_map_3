package net.fragment

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.layout_f_n.*
import net.adapter.NearAdapter
import net.basicmodel.R
import net.utils.NearClickListener
import net.utils.ResourceManager

class FragmentN(val listener: NearClickListener) : BaseFragment() {
    override fun initView() {

    }

    override fun initData() {
        resData =
            ResourceManager.get().getResource(requireActivity(), R.mipmap::class.java, "mipmap")
        val adapter = NearAdapter(activity, R.layout.layout_item_nearby, resData)
        recyclerview.layoutManager = GridLayoutManager(activity, 3)
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                listener.click(resData!![p2])
            }

            override fun onItemLongClick(
                p0: View?,
                p1: RecyclerView.ViewHolder?,
                p2: Int
            ): Boolean {
                return false
            }

        })
    }

    override fun getLayout(): Int {
        return R.layout.layout_f_n
    }
}