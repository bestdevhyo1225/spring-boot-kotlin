package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.service.dto.FindMemberDto

interface MemberQueryRepository {
    fun findWithFetchJoinById(id: Long): Member?
    fun paginationNoOffset(id: Long, pageSize: Long): List<Member>
    fun paginationCoveringIndex(pageNo: Long, pageSize: Long): List<FindMemberDto>
}
