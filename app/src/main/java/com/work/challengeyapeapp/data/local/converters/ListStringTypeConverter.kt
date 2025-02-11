package com.work.challengeyapeapp.data.local.converters

import androidx.room.TypeConverter

class ListStringTypeConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return data.split(",")
    }
}
