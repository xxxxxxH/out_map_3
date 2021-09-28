package net.adapter

import android.app.Activity
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.itheima.roundedimageview.RoundedImageView
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import net.basicmodel.R
import net.entity.ResourceEntity
import net.utils.ResourceManager
import net.utils.ScreenUtils

class NearAdapter(val activity: Activity?, layoutId: Int, datas: ArrayList<ResourceEntity>?) :
    CommonAdapter<ResourceEntity>(activity, layoutId, datas) {
    override fun convert(holder: ViewHolder?, t: ResourceEntity?, position: Int) {
        holder!!.getView<RelativeLayout>(R.id.item_root).let {
            it.layoutParams = it.layoutParams.apply {
                width = ScreenUtils.getScreenSize(activity)[1] / 3
            }
        }

        holder.getView<RoundedImageView>(R.id.item_bg).let {
            it.layoutParams = it.layoutParams.apply {
                 width = ScreenUtils.getScreenSize(activity)[1] / 4
                height = ScreenUtils.getScreenSize(activity)[0] / 4
            }
            Glide.with(activity!!).load(ResourceManager.get().resTString(activity, t!!.id)).into(it)
        }

        holder.setText(R.id.item_name, t!!.name)
    }
}