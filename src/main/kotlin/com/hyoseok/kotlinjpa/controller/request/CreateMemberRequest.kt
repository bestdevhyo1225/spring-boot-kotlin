package com.hyoseok.kotlinjpa.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class CreateMemberRequest {

    @get:NotEmpty(message = "username은 반드시 입력해야 합니다.")
    lateinit var username: String

    @get:Email(message = "email 형식이 맞지 않습니다.")
    lateinit var email: String

    @get:NotEmpty(message = "team name은 반드시 입력해야 합니다.")
    lateinit var teamName: String

}
