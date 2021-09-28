package net.entity

import java.io.Serializable

data class DataEntity(
    var imageUrl: String,
    var key: String,
    var title: String,
    var desc: String,
    var lat: Double,
    var lng: Double,
    var panoid: String,
    var isFife: Boolean,
    var pannoId: String
) : Serializable