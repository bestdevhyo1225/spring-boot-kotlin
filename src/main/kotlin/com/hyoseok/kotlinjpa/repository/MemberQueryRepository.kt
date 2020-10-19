package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member

interface MemberQueryRepository {
    fun findWithFetchJoinById(id: Long): Member?
}