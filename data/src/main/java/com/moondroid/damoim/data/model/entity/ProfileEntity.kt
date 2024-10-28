package com.moondroid.damoim.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moondroid.damoim.common.RequestParam

/**
 * User Model class for Room
 * */
@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RequestParam.ID)
    val id: String,
    @ColumnInfo(name = RequestParam.NAME)
    val name: String,
    @ColumnInfo(name = RequestParam.BIRTH)
    val birth: String,
    @ColumnInfo(name = RequestParam.GENDER)
    val gender: String,
    @ColumnInfo(name = RequestParam.LOCATION)
    val location: String,
    @ColumnInfo(name = RequestParam.INTEREST)
    val interest: String,
    @ColumnInfo(name = RequestParam.THUMB)
    val thumb: String,
    @ColumnInfo(name = RequestParam.MESSAGE)
    val message: String
)
