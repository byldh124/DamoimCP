package com.moondroid.damoim.common

object Constants{
    const val DEFAULT_PROFILE_IMG =
        "http://moondroid.dothome.co.kr/damoim/thumbs/IMG_20210302153242unnamed.jpg"

    const val PREFS_NAME = "pref"

    const val NETWORK_NOT_CONNECTED = -200

    /* Room Table name */
    const val DM_USER = "DMUser"
}

object ResponseCode {
    const val SUCCESS = 1000
    const val FAIL = 2000
    const val NOT_EXIST = 2001
    const val ALREADY_EXIST = 2002
    const val INVALID_VALUE = 2003
    const val INACTIVE = 2004
}

enum class GroupType {
    ALL,
    FAVORITE,           // 관심등록 모임
    RECENT,             // 최근 본 모임
    MY_GROUP,           // 내 모임
}

object RequestParam {
    /** 유저 관련 **/
    const val ID = "id"
    const val HASH_PW = "hashPw"
    const val NAME = "name"
    const val THUMB = "thumb"
    const val SALT = "salt"
    const val LOCATION = "location"
    const val INTEREST = "interest"
    const val BIRTH = "birth"
    const val GENDER = "gender"
    const val MESSAGE = "message"
    const val TOKEN = "token"

    /** 그룹 관련 **/
    const val TITLE = "title"
    const val PURPOSE = "purpose"
    const val IMAGE = "image"
    const val INFORMATION = "information"
    const val MASTER_ID = "masterId"
    const val MEMBER = "member"
    const val IS_MEMBER = "isMember"
    const val ORIGIN_TITLE = "originTitle"


    /** 정모 관련 **/
    const val ADDRESS = "address"
    const val DATE = "date"
    const val PAY = "pay"
    const val LAT = "lat"
    const val LNG = "lng"
    const val JOIN_MEMBER = "joinMember"
    const val TIME = "time"
    const val LatLng = "latLng"

    /** 신고, 차단 관련 **/
    const val BLOCK_ID = "blockId"

    /** 채팅 관련 **/
    const val OTHER = "other"

    /** 일반 **/
    const val LAST_TIME = "lastTime"
    const val ACTIVE = "active"
    const val FAVOR = "favor"

    /** Response **/
    const val CODE = "code"
    const val RESULT = "result"
}

object IntentParam {
    const val ID = "ID"
    const val NAME = "NAME"
    const val THUMB = "THUMB"
    const val ACTIVITY = "ACTIVITY"
    const val INTEREST = "INTEREST"
    const val INTEREST_ICON = "INTEREST_ICON"
    const val LOCATION = "LOCATION"
    const val ADDRESS = "ADDRESS"
    const val TYPE = "TYPE"
    const val USER = "USER"
    const val MOIM = "MOIM"
    const val REPORT_ID = "REPORT_ID"
    const val REPORT_NAME = "REPORT_NAME"
    const val SHOW_TUTORIAL = "SHOW_TUTORIAL"
    const val LOCATION_TO_ADDRESS = "LOCATION_TO_ADDRESS"
    const val USER_IS_MEMBER_THIS_GROUP = "USER_IS_MEMBER_THIS_GROUP"

    const val CROP_IMAGE_WITH_RATIO = "CROP_IMAGE_WITH_RATIO"
}

object NotificationParam {
    const val TITLE = "title"
    const val BODY = "body"
    const val DATA = "data"
}

object GroupListType {
    const val FAVORITE = 1
    const val RECENT = 2
}

object DMRegex {
    val ID = Regex("^[a-zA-Z0-9]{5,16}$")                                                   // 아이디 정규식 [영문, 숫자 5-16 글자]
    val PW = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@!%*#?&]{8,}\$")                      // 비밀번호 정규식 [영문, 숫자 8글자 이상]
    val TITLE = Regex("^(.{2,20})$")                                                        // 모임이름 정규식 [2-20 글자]
    val NAME = Regex("^(.{2,8})$")                                                          // 이름 정규식 [2-8 글자]
}


