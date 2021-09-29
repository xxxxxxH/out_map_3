package net.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.layout_f_i.*
import net.adapter.PlaceAdapter1
import net.basicmodel.DetailsActivity
import net.basicmodel.R
import net.utils.Constant
import net.utils.NetUtils

class FragmentI: BaseFragment() {
    override fun initView() {
        EasyHttp.get(Constant.BASE_URL + Constant.URI_BIG)
            .execute<String>(object : SimpleCallBack<String>() {
                override fun onError(e: ApiException?) {
                    e!!.printStackTrace()
                }

                override fun onSuccess(t: String?) {
                    data = NetUtils.get().get(t!!)
                    val adapter = PlaceAdapter1(
                        activity!!,
                        R.layout.layout_item_interactive,
                        data
                    )
                    recycler.layoutManager = LinearLayoutManager(activity!!)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object :MultiItemTypeAdapter.OnItemClickListener{
                        override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                            val i = Intent(activity, DetailsActivity::class.java)
                            i.putExtra("data", adapter.datas[p2])
                            activity!!.startActivity(i)
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

            })
    }

    override fun initData() {

    }

    override fun getLayout(): Int {
        return R.layout.layout_f_i
    }
}