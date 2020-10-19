package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.Team
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager

// @SpringBootTest -> Component Scan을 함
@DataJpaTest // Component Scan을 하지 않음
@DisplayName("MemberRepository 테스트")
internal class MemberRepositoryTest(
        @Autowired private val memberRepository: MemberRepository,
        @Autowired private val entityManager: EntityManager
) {

    @Test
    @DisplayName("Member 엔티티 저장")
    fun save() {
        // given
        val username = "hyoseok"
        val email = "test@gmail.com"
        val team = Team("teamA")
        val member = Member(username, email, team)

        // when
        val memberId = memberRepository.save(member).id

        entityManager.flush()
        entityManager.clear()

        val findMember = memberRepository.findById(memberId!!)
                .orElseThrow { NoSuchElementException("존재하지 않는 회원입니다.") }

        // then
        assertThat(findMember!!.id).isEqualTo(memberId)
        println("------------- 데이터 접근하기 전 -------------")
        assertThat(findMember.team.name).isEqualTo(team.name)
        println("------------- 데이터 접근 후 -------------")
    }

    @Test
    @DisplayName("Member 엔티티 수정")
    fun update() {
        // given
        val member = memberRepository.save(Member("hyoseok", "test@gmail.com", Team("teamA")));

        entityManager.flush()
        entityManager.clear()

        // when
        val findMember = memberRepository.findById(member.id!!)
                .orElseThrow { NoSuchElementException("존재하지 않는 회원입니다.") }

        findMember.change("jang hyo seok", "hyoseok@gmail.com")

        entityManager.flush()
        entityManager.clear()

        // then
        assertThat(findMember.username).isEqualTo("jang hyo seok")
        assertThat(findMember.email).isEqualTo("hyoseok@gmail.com")
    }

}
