package net.utils

import android.util.Log
import net.entity.DataEntity
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Copyright (C) 2021,2021/9/29, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class NetUtils {
    companion object {
        private var instance: NetUtils? = null
            get() {
                field ?: run {
                    field = NetUtils()
                }
                return field
            }

        @Synchronized
        fun get(): NetUtils {
            return instance!!
        }
    }

    fun get(json: String): ArrayList<DataEntity> {
        val result = ArrayList<DataEntity>()
        try {
            val jsonObject = JSONObject(json)
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val next = iterator.next()
                val jsonObject1 = jsonObject.getJSONObject(next)
                val entity = DataEntity()
                entity.title = jsonObject1.getString("title")
                entity.lat = jsonObject1.getDouble("lat")
                entity.lng = jsonObject1.getDouble("lng")
                entity.key = next
                entity.panoid = jsonObject1.getString("panoid")
                if (entity.panoid == "LiAWseC5n46JieDt9Dkevw") {
                    continue
                }
                if (jsonObject1.has("isFife")) {
                    entity.fife = jsonObject1.getBoolean("isFife")
                    continue
                }
                if (entity.fife) {
                    entity.imageUrl =
                        "https://lh4.googleusercontent.com/" + entity.panoid + "/w400-h300-fo90-ya0-pi0/"
                    continue
                } else {
                    entity.imageUrl =
                        "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + jsonObject1.getString(
                            "panoid"
                        )
                }
                result.add(entity)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.i("xxxxxxH", "e = $e")
        }
        return result
    }

    fun getSmall(big: DataEntity, json: String): ArrayList<DataEntity> {
        val result = ArrayList<DataEntity>()
        try {
            val jsonObject = JSONObject(json)
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val next = iterator.next()
                val jsonObject1 = jsonObject.getJSONObject(next)
                val entity = DataEntity()
                entity.title = jsonObject1.getString("title")
                entity.lat = jsonObject1.getDouble("lat")
                entity.lng = jsonObject1.getDouble("lng")
                entity.pannoId = jsonObject1.getString("panoid")
                if (big.fife) {
                    entity.imageUrl =
                        "https://lh4.googleusercontent.com/" + entity.pannoId + "/w400-h300-fo90-ya0-pi0/"
                } else {
                    entity.imageUrl =
                        "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + jsonObject1
                            .getString("panoid")
                }
                result.add(entity)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.i("xxxxxxH", "e = $e")
        }
        return result
    }
}