package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MemberRepository: JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.team where m.id = :id")
    fun findWithFetchJoinById(id: Long): Optional<Member>

}
