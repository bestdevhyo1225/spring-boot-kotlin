package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
}
