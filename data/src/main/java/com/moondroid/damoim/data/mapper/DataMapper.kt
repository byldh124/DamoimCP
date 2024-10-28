package com.moondroid.damoim.data.mapper

import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile

object DataMapper {
    fun ProfileDTO.toProfileEntity(): ProfileEntity =
        ProfileEntity(id, name, birth, gender, location, interest, thumb, message)

    fun ProfileEntity.toProfile(): Profile = Profile(id, name, birth, gender, location, interest, thumb, message)

    fun ProfileDTO.toProfile(): Profile = Profile(id, name, birth, gender, location, interest, thumb, message)

    fun MoimItemDTO.toMoimItem(): MoimItem = MoimItem(title, address, date, time, pay, lat, lng, joinMember)

    fun GroupItemDTO.toGroupItem(): GroupItem =
        GroupItem(no, title, location, purpose, interest, thumb, image, information, masterId)
}