package com.hyoseok.kotlinjpa.service

import com.hyoseok.kotlinjpa.entity.Member
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional(readOnly = true)
@DisplayName("MemberService 테스트")
internal class MemberServiceTest(
        @Autowired private val memberService: MemberService,
        @Autowired private val entityManager: EntityManager
) {

    @Test
    @Transactional
    @DisplayName("회원을 생성한다.")
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
    @Transactional
    @DisplayName("회원의 정보를 수정한다.")
    fun updateMember() {
        // given
        val memberId: Long = memberService.createMember("hyoseok", "test1234@gmail.com", "teamA")
        val changeUsername = "change name"
        val changeEmail = "change email"

        entityManager.flush()
        entityManager.clear()

        // when
        memberService.updateMember(memberId, changeUsername, changeEmail)

        entityManager.flush()
        entityManager.clear()

        val member: Member = memberService.findMember(memberId)!!

        // then
        assertThat(member.username).isEqualTo(changeUsername)
        assertThat(member.email).isEqualTo(changeEmail)
    }

}
