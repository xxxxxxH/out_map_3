package net.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import net.entity.ResourceEntity
import java.util.*

class ResourceManager {
    companion object {
        private var instance: ResourceManager? = null
            get() {
                field?.let { } ?: run {
                    field = ResourceManager()
                }
                return field
            }

        @Synchronized
        fun get(): ResourceManager {
            return instance!!
        }
    }

    fun getResource(context: Context, clazz: Class<*>, filter: String): ArrayList<ResourceEntity> {
        return getResourceByFolder(context, clazz, filter)
    }


    private fun getResourceByFolder(
        context: Context,
        clazz: Class<*>,
        filter: String
    ): ArrayList<ResourceEntity> {
        val result = ArrayList<ResourceEntity>()
        for (field in clazz.fields) {
            val name = field.name
            if (name.startsWith("ic")) {
                continue
            }
            val id = context.resources.getIdentifier(name, filter, context.packageName)
            val entity = ResourceEntity(name, id)
            result.add(entity)
        }
        return result
    }

    fun resTString(context: Context, id: Int): String {
        return res2String(context, id)
    }

    private fun res2String(context: Context, id: Int): String {
        val r = context.resources
        val uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(id) + "/"
                    + r.getResourceTypeName(id) + "/"
                    + r.getResourceEntryName(id)
        )
        return uri.toString()
    }
}