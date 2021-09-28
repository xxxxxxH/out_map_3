package net.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_f_m.*
import net.basicmodel.R
import net.utils.MapUtils

@SuppressLint("MissingPermission")
class FragmentM : BaseFragment() {

    var mMap: GoogleMap? = null

    override fun initView() {
        mapview.onCreate(bundle)
        mapview.onResume()
        MapsInitializer.initialize(activity)

        val errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)
        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, activity, 0).show()
        } else {
            mapview.getMapAsync {
                mMap = it
                mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                it.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.73, -73.99)))
                mMap!!.uiSettings.isZoomControlsEnabled = true
                mMap!!.isMyLocationEnabled = true
                mMap!!.uiSettings.isMyLocationButtonEnabled = true
                mMap!!.setOnMyLocationButtonClickListener {
                    val locationManager: LocationManager =
                        activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
                    if (location.latitude != 0.0 && location.longitude != 0.0) {
                        mMap!!.animateCamera(
                            MapUtils.get().getPosition(
                                location.latitude,
                                location.longitude

                            ), 1000, null
                        )
                    } else {
                        mMap!!.animateCamera(
                            MapUtils.get().getPosition(
                                33.9204, -117.9465
                            ), 1000, null
                        )
                    }
                    false
                }
                mMap!!.setOnMyLocationClickListener {
                    Toast.makeText(activity, "Current location:\n$it", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, activity)
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(activity, toastMsg, Toast.LENGTH_LONG).show()
                mMap!!.addMarker(MarkerOptions().position(place.latLng).title("Marker in Sydney"))
                mMap!!.animateCamera(
                    MapUtils.get().getPosition(place.latLng.latitude, place.latLng.longitude),
                    1000,
                    null
                )
            }
        }
    }

    override fun initData() {

    }

    override fun getLayout(): Int {
        return R.layout.layout_f_m
    }

}