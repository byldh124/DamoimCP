package com.moondroid.project01_meetingapp.ui.features.common.interest

import androidx.annotation.DrawableRes
import com.moondroid.project01_meetingapp.R

data class InterestIcon(
    val name: String,
    @DrawableRes
    val drawable: Int,
)

val interestIconList = arrayListOf(
    InterestIcon("아웃도어/여행", R.drawable.ic_interest_01),
    InterestIcon("운동/스포츠", R.drawable.ic_interest_02),
    InterestIcon("인문학/책/글", R.drawable.ic_interest_03),
    InterestIcon("업종/직무", R.drawable.ic_interest_04),
    InterestIcon("외국/언어", R.drawable.ic_interest_05),
    InterestIcon("문화/공연/축제", R.drawable.ic_interest_06),
    InterestIcon("음악/악기", R.drawable.ic_interest_07),
    InterestIcon("공예/만들기", R.drawable.ic_interest_08),
    InterestIcon("댄스/무용", R.drawable.ic_interest_09),
    InterestIcon("봉사활동", R.drawable.ic_interest_10),
    InterestIcon("사교/인맥", R.drawable.ic_interest_11),
    InterestIcon("차/오토바이", R.drawable.ic_interest_12),
    InterestIcon("사진/영상", R.drawable.ic_interest_13),
    InterestIcon("야구관람", R.drawable.ic_interest_14),
    InterestIcon("게임/오락", R.drawable.ic_interest_15),
    InterestIcon("요리/제조", R.drawable.ic_interest_16),
    InterestIcon("반려동물", R.drawable.ic_interest_17),
    InterestIcon("가족/결혼", R.drawable.ic_interest_18),
    InterestIcon("자유주제", R.drawable.ic_interest_19),
)