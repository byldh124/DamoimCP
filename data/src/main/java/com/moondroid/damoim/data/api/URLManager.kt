package com.moondroid.damoim.data.api

object URLManager {
    const val BASE_URL = "http://moondroid.dothome.co.kr/damoim/"

    /** 앱 기능 관련 **/
    const val ChECK_APP_VERSION = "app/checkVersion.php"

    /** 그룹 정보 관련 **/
    const val GET_GROUP = "group/group.php"
    const val GET_MEMBER = "group/member.php"
    const val GET_MY_GROUP = "group/myGroup.php"
    const val GET_FAVORITE = "group/favorite.php"
    const val GET_RECENT = "group/recent.php"

    /** 모임 관련 **/
    const val GET_MOIM = "moim/moim.php"
    const val CREATE_MOIM = "moim/create.php"
    const val GET_MOIM_MEMBER = "moim/member.php"
    const val JOIN_INTO_MOIM = "moim/join.php"

    /** 유저-그룹 관련 **/
    const val SAVE_RECENT = "user/updateRecent.php"
    const val SAVE_FAVOR = "user/updateFavor.php"
    const val JOIN = "user/join.php"
    const val GET_FAVOR = "user/favor.php"

    /** 그룹 생성 수정 **/
    const val CREATE_GROUP = "group/create.php"
    const val UPDATE_GROUP = "group/update.php"

    /** 회원가입 로그인 관련 **/
    const val SIGN_IN = "sign/signIn.php"
    const val SIGN_UP = "sign/signUp.php"
    const val SIGN_IN_SOCIAL = "sign/signInKakao.php"
    const val SALT = "sign/salt.php"
    const val RESIGN = "sign/resign.php"

    /** 유저 정보 관련 **/
    const val UPDATE_TOKEN = "user/updateToken.php"
    const val UPDATE_PROFILE = "user/updateProfile.php"
    const val UPDATE_INTEREST = "user/updateInterest.php"

    /** 차단, 신고 **/
    const val BLOCK = "app/block.php"
    const val REPORT = "app/report.php"
}