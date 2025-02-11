package com.work.challengeyapeapp.data.local.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.work.challengeyapeapp.data.local.converters.ListStringTypeConverter

@Entity(tableName = "recipe")
@TypeConverters(ListStringTypeConverter::class)
data class RecipeEntity(
    @PrimaryKey
    @ColumnInfo(name = "tr_id") val id: Int,
    @ColumnInfo(name = "tr_name") val name: String,
    @ColumnInfo(name = "tr_description") val description: String,
    @ColumnInfo(name = "tr_category") val category: String,
    @ColumnInfo(name = "tr_location") val location: String,
    @ColumnInfo(name = "tr_ingredients") val ingredients: List<String>,
    @ColumnInfo(name = "tr_image") val image: String
)

@TypeConverter
fun fromListString(list: List<String>): String {
    return list.joinToString(",")
}
