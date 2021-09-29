package net.basicmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.StreetViewPanoramaCamera
import com.google.android.gms.maps.model.StreetViewPanoramaLocation
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.layout_activity_details.*
import net.adapter.SmallAdapter
import net.entity.DataEntity
import net.utils.Constant
import net.utils.NetUtils
import java.util.*

/**
 * Copyright (C) 2021,2021/9/29, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class DetailsActivity : AppCompatActivity() {

    var entity: DataEntity? = null
    var data: ArrayList<DataEntity>? = null
    private var mStreetViewPanorama: StreetViewPanorama? = null
    var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_details)
        val intent = intent
        entity = intent.getSerializableExtra("data") as DataEntity
        getDatas(entity!!.key)
        try {
            initMap(savedInstanceState)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun getDatas(key: String) {
        EasyHttp.get(Constant.BASE_URL + "streetview/feed/gallery/collection/$key.json")
            .execute<String>(object : SimpleCallBack<String>() {
                override fun onError(e: ApiException?) {
                    e!!.printStackTrace()
                }

                override fun onSuccess(t: String?) {
                    data = NetUtils.get().getSmall(entity!!, t!!)
                    val adapter =
                        SmallAdapter(this@DetailsActivity, R.layout.layout_item_interactive, data)
                    recycler.layoutManager = LinearLayoutManager(this@DetailsActivity)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        MultiItemTypeAdapter.OnItemClickListener {
                        override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                            val dataEntity = adapter.datas[p2]
                            if (mStreetViewPanorama == null) {
                                Toast.makeText(
                                    this@DetailsActivity,
                                    "Street view init failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            }
                            if (entity!!.fife) {
                                mStreetViewPanorama!!.setPosition("F:" + dataEntity.pannoId)
                            } else {
                                mStreetViewPanorama!!.setPosition(dataEntity.pannoId)
                            }
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

    private fun initMap(savedInstanceState: Bundle?) {
        val streetF =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportStreetViewPanoramaFragment?
        streetF?.getStreetViewPanoramaAsync { p0 ->
            mStreetViewPanorama = p0
            mStreetViewPanorama!!.setOnStreetViewPanoramaChangeListener { }
            if (savedInstanceState == null) {
                mStreetViewPanorama?.setPosition(entity!!.panoid)
            }
            routeMap()
            mStreetViewPanorama!!.setOnStreetViewPanoramaClickListener { streetViewPanoramaOrientation: StreetViewPanoramaOrientation? ->
                if (timer != null) {
                    timer!!.cancel()
                }
            }
        }
    }

    private fun routeMap() {
        if (timer != null) {
            timer!!.cancel()
        }
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val camera = StreetViewPanoramaCamera.builder()
                        .zoom(mStreetViewPanorama!!.panoramaCamera.zoom)
                        .tilt(mStreetViewPanorama!!.panoramaCamera.tilt)
                        .bearing(mStreetViewPanorama!!.panoramaCamera.bearing + 60)
                        .build()
                    mStreetViewPanorama!!.animateTo(camera, 5000)
                }
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 1000, 5000)
    }

}