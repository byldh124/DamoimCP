package com.moondroid.damoim.common.constant

object DMRegex {
    val ID = Regex("^[a-zA-Z0-9]{5,16}$")                                                   // 아이디 정규식 [영문, 숫자 5-16 글자]
    val PW = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@!%*#?&]{8,}\$")                      // 비밀번호 정규식 [영문, 숫자 8글자 이상]
    val TITLE = Regex("^(.{2,20})$")                                                        // 모임이름 정규식 [2-20 글자]
    val NAME = Regex("^(.{2,8})$")                                                          // 이름 정규식 [2-8 글자]
}
