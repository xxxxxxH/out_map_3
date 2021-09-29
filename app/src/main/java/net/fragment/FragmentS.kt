package net.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import net.basicmodel.R
import net.utils.MapUtils

@SuppressLint("MissingPermission")
class FragmentS : BaseFragment() {
    var sydeny = LatLng(-33.87365, 151.20689)
    var mStreetViewPanorama: StreetViewPanorama? = null
    var marker: Marker? = null
    var markerPosition: LatLng? = null
    override fun initView() {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        markerPosition = if (location != null) {
            LatLng(location.latitude, location.longitude)
        } else {
            sydeny
        }
        val panoramaFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.streetviewpanorama) as SupportStreetViewPanoramaFragment?
        panoramaFragment?.getStreetViewPanoramaAsync { streetViewPanorama ->
            mStreetViewPanorama = streetViewPanorama
            mStreetViewPanorama!!.setOnStreetViewPanoramaChangeListener { streetViewPanoramaLocation ->
                if (streetViewPanoramaLocation != null) {
                    marker!!.position = streetViewPanoramaLocation.position
                }
            }
            mStreetViewPanorama!!.setPosition(markerPosition)
        }

        val mapFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.mapstreet) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            googleMap.setOnMarkerDragListener(object : OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {}
                override fun onMarkerDrag(marker: Marker) {}
                override fun onMarkerDragEnd(marker: Marker) {
                    mStreetViewPanorama!!.setPosition(marker.position, 150)
                }
            })
            googleMap.moveCamera(
                MapUtils.get().getPosition(markerPosition!!.latitude, markerPosition!!.longitude)
            )
            marker = googleMap.addMarker(
                MapUtils.get().getMarkerOptions(
                    markerPosition!!.latitude,
                    markerPosition!!.longitude
                )

            )
            googleMap.setOnMapClickListener { latLng ->
                googleMap.clear()

                marker =
                    googleMap.addMarker(
                        MapUtils.get().getMarkerOptions(latLng.latitude, latLng.longitude)
                    )
                mStreetViewPanorama!!.setPosition(latLng)
            }
        }
    }

    override fun initData() {

    }

    override fun getLayout(): Int {
        return R.layout.layout_f_s
    }
}