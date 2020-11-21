package com.hyoseok.kotlinjpa.service

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.Team
import com.hyoseok.kotlinjpa.repository.MemberQueryRepository
import com.hyoseok.kotlinjpa.repository.MemberRepository
import com.hyoseok.kotlinjpa.service.dto.FindMemberDto
import com.hyoseok.kotlinjpa.service.exception.ErrorMessage
import com.hyoseok.kotlinjpa.service.exception.NotFoundMemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
        private val memberQueryRepository: MemberQueryRepository,
        private val memberRepository: MemberRepository
) {

    fun findMembersByNoOffset(id: Long, pageSize: Long): List<FindMemberDto> {
        return memberQueryRepository.paginationNoOffset(id, pageSize)
                .map { FindMemberDto(it.id, it.username, it.email, it.team!!.name) }
    }

    fun findMembersByConveringIndex(pageNo: Long, pageSize: Long): List<FindMemberDto> {
        return memberQueryRepository.paginationCoveringIndex(pageNo - 1, pageSize)
    }

    fun findMember(memberId: Long): FindMemberDto? {
        /*
        * ?: 연산자 (Elvis operator)
        * 좌항이 null이면, null을 반환한다.
        * */
        val member: Member = memberQueryRepository.findWithFetchJoinById(memberId)
                ?: throw NotFoundMemberException(ErrorMessage.NOT_FOUND_MEMBER)

        return FindMemberDto(null, member.username, member.email, member.team!!.name)
    }

    @Transactional
    fun createMember(username: String, email: String, teamName: String): Long {
        val member = Member(username, email, Team(teamName))
        return memberRepository.save(member).id
    }

    @Transactional
    fun updateMember(memberId: Long, username: String, email: String) {
        val member = memberRepository.findById(memberId)
                .orElseThrow { NotFoundMemberException(ErrorMessage.NOT_FOUND_MEMBER) }

        member.change(username, email)
    }

}
