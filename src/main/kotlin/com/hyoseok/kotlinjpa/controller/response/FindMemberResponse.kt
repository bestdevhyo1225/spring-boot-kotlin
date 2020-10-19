package com.hyoseok.kotlinjpa.controller.response

class FindMemberResponse(id: Long, username: String, email: String, teamName: String) {

    val id: Long? = id

    val username: String? = username

    val email: String? = email

    val teamName: String? = teamName

}