package com.hyoseok.kotlinjpa.service.dto

class FindMemberDto(id: Long, username: String, email: String, teamName: String) {
    var id: Long? = id

    var username: String? = username

    var email: String? = email

    var teamName: String? = teamName

}
