package net.adapter

import android.app.Activity
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import net.basicmodel.R
import net.entity.DataEntity
import net.utils.ScreenUtils

/**
 * Copyright (C) 2021,2021/9/29, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class SmallAdapter(val activity: Activity, layoutId: Int, datas: ArrayList<DataEntity>?) :
    CommonAdapter<DataEntity>(activity, layoutId, datas) {
    override fun convert(holder: ViewHolder?, t: DataEntity?, position: Int) {
        holder!!.getView<RelativeLayout>(R.id.root)?.let {
            it.layoutParams = it.layoutParams.apply {
                height = ScreenUtils.getScreenSize(activity)[0] / 4
            }
        }
        holder.getView<ImageView>(R.id.img)?.let {
            it.layoutParams = it.layoutParams.apply {
                height = (ScreenUtils.getScreenSize(activity)[0] / 4) - 10
            }
            Glide.with(activity).load(t!!.imageUrl).into(it)
        }

        holder.setText(R.id.img_name, t!!.title)
    }
}