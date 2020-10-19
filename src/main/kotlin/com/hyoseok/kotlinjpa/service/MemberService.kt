package com.hyoseok.kotlinjpa.service

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.Team
import com.hyoseok.kotlinjpa.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
        private val memberRepository: MemberRepository
) {

    fun findMembers(): MutableList<Member> {
        return memberRepository.findAll()
    }

    fun findMember(memberId: Long): Member? {
        return memberRepository.findById(memberId)
                .orElseThrow { NoSuchElementException("존재하지 않는 회원입니다.") }
    }

    @Transactional
    fun createMember(username: String, email: String, teamName: String): Long {
        val member = Member(username, email, Team(teamName))
        return memberRepository.save(member).id!!
    }

    @Transactional
    fun updateMember(memberId: Long, username: String, email: String) {
        val member = memberRepository.findById(memberId)
                .orElseThrow { NoSuchElementException("존재하지 않는 회원입니다.") }

        member.change(username, email)
    }

}
