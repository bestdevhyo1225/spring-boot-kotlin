package com.hyoseok.kotlinjpa.service

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.repository.MemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional(readOnly = true)
@DisplayName("MemberService 테스트")
internal class MemberServiceTest(
        @Autowired private val memberRepository: MemberRepository,
        @Autowired private val memberService: MemberService
) {

    @AfterEach
    fun tearDown() {
        memberRepository.deleteAll()
    }

    @Test
    @DisplayName("회원을 생성한다.")
    @Transactional
    fun createMember() {
        // given
        val username = "hyoseok"
        val email = "test1234@gmail.com"
        val teamName = "teamA"

        // when
        val memberId: Long = memberService.createMember(username, email, teamName)
        val member: Member = memberService.findMember(memberId)!!

        // then
        assertThat(member.id).isEqualTo(memberId)
        assertThat(member.username).isEqualTo(username)
        assertThat(member.email).isEqualTo(email)
        assertThat(member.team.name).isEqualTo(teamName)
    }

    @Test
    @DisplayName("회원의 정보를 수정한다.")
    @Transactional
    @Commit
    fun updateMember() {
        // given
        val memberId: Long = memberService.createMember("hyoseok", "test1234@gmail.com", "teamA")
        val changeUsername = "change name"
        val changeEmail = "change email"

        // when
        memberService.updateMember(memberId, changeUsername, changeEmail)
        val member: Member = memberService.findMember(1L)!!

        // then
        assertThat(member.username).isEqualTo(changeUsername)
        assertThat(member.email).isEqualTo(changeEmail)
    }

}
