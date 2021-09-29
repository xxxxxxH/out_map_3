package net.utils

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.basicmodel.R

class MapUtils {
    companion object{
        private var instance:MapUtils?=null
        get() {
            field?.let {  }?:run {
                field = MapUtils()
            }
            return field
        }

        @Synchronized
        fun get():MapUtils{
            return instance!!
        }
    }

    fun getPosition(la: Double, lo: Double): CameraUpdate {
        val position = CameraPosition.builder().target(LatLng(la, lo))
            .zoom(15.5f)
            .bearing(0f)
            .tilt(25f)
            .build()
        return CameraUpdateFactory.newCameraPosition(position)
    }

    fun getMarkerOptions(la: Double, lo: Double): MarkerOptions {
        return MarkerOptions().position(LatLng(la, lo))
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)).draggable(true)
    }
}