package com.moondroid.damoim.data.mapper

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.moondroid.damoim.domain.model.Profile

@ProvidedTypeConverter
class DataTypeConverter {

    @TypeConverter
    fun profileToJsonString(profile: Profile): String {
        return Gson().toJson(profile)
    }

    @TypeConverter
    fun jsonToProfile(json: String): Profile {
        return Gson().fromJson(json, Profile::class.java)
    }

    @TypeConverter
    fun listToJson(value: List<Profile>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Profile>? {
        return Gson().fromJson(value,Array<Profile>::class.java)?.toList()
    }

}