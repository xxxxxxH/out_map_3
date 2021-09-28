package net.utils

import net.entity.ResourceEntity

interface NearClickListener {
    fun click(entity: ResourceEntity)
}