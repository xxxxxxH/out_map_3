package net.basicmodel

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weeboos.permissionlib.PermissionRequest
import com.zhouyou.http.EasyHttp
import kotlinx.android.synthetic.main.activity_main.*
import net.entity.ResourceEntity
import net.event.MessageEvent
import net.fragment.FragmentI
import net.fragment.FragmentM
import net.fragment.FragmentN
import net.fragment.FragmentS
import net.utils.MapTypeClickListener
import net.utils.NearClickListener
import net.utils.PackageUtils
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity(), NearClickListener,MapTypeClickListener {
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val title = arrayOf("map", "nearby", "streetView", "interActive")
    private var views: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EasyHttp.init(application)
        requestPermissions()
    }

    private fun requestPermissions() {
        PermissionRequest.getInstance().build(this)
            .requestPermission(object : PermissionRequest.PermissionListener {
                override fun permissionGranted() {
                    initView()
                }

                override fun permissionDenied(permissions: java.util.ArrayList<String>?) {
                    finish()
                }

                override fun permissionNeverAsk(permissions: java.util.ArrayList<String>?) {
                    finish()
                }

            }, permissions)
    }

    private fun initView() {
        views = ArrayList()
        views!!.add(FragmentM(this))
        views!!.add(FragmentN(this))
        views!!.add(FragmentS())
        views!!.add(FragmentI())
        tab.setViewPager(viewpager, title, this, views)
    }

    override fun click(entity: ResourceEntity) {
        if (!PackageUtils.get().checkAppInstalled(this, "com.google.android.apps.maps")) {
            Toast.makeText(this, "not found google map", Toast.LENGTH_SHORT).show()
            return
        }
        PackageUtils.get()
            .startApp(this, Uri.parse("http://maps.google.com/maps?q=" + entity.name + "&hl=en"))
    }

    override fun typeClick(type: String) {
        when(type){
            "search"->{
                EventBus.getDefault().post(MessageEvent("search"))
            }
            "n"->{
                EventBus.getDefault().post(MessageEvent("n"))
            }
            "h"->{
                EventBus.getDefault().post(MessageEvent("h"))
            }
            "s"->{
                EventBus.getDefault().post(MessageEvent("s"))
            }
            "t"->{
                EventBus.getDefault().post(MessageEvent("t"))
            }
        }
    }
}