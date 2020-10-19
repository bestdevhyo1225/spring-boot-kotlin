package com.hyoseok.kotlinjpa.service

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.Team
import com.hyoseok.kotlinjpa.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {

    @Transactional
    fun createMember(username: String, email: String, teamName: String): Long? {
        val member = Member(username, email, Team(teamName))
        return memberRepository.save(member).id
    }

}
