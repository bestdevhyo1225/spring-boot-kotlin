package com.hyoseok.kotlinjpa.controller.response

import com.hyoseok.kotlinjpa.service.dto.FindMemberDto

class FindMembersResponse(
        val members: List<FindMemberDto>?
)
