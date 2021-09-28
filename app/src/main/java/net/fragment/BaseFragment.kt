package net.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.entity.DataEntity
import net.entity.ResourceEntity

abstract class BaseFragment : Fragment() {

    protected var data: ArrayList<DataEntity>? = null
    protected var resData: ArrayList<ResourceEntity>? = null
    protected var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = savedInstanceState
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun getLayout(): Int
}