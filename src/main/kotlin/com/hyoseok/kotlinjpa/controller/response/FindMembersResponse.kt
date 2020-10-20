package com.hyoseok.kotlinjpa.controller.response

import com.hyoseok.kotlinjpa.service.dto.FindMemberDto

class FindMembersResponse(members: List<FindMemberDto>) {

    val members: List<FindMemberDto>? = members

}
