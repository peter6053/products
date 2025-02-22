package com.peter.pezesha.data.db.typeConverters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class ProductsTypeConverters {
    @TypeConverter
    fun convertImagesToString(images: List<String>): String {
        return Json.encodeToString(images)
    }

    @TypeConverter
    fun convertStringToImages(images: String) : List<String> {
        return Json.decodeFromString(images)
    }
}


