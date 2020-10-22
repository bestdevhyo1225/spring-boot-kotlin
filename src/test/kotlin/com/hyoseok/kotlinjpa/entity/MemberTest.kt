package com.hyoseok.kotlinjpa.entity

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Member 도메인 테스트")
internal class MemberTest {

    @Test
    @DisplayName("Member를 생성한다.")
    fun createMember() {
        // given
        val username = "hyoseok"
        val email = "test@gmail.com"
        val team = Team("teamA")

        // when
        val member = Member(username, email, team)

        // then
        assertThat(member.username).isEqualTo(username)
        assertThat(member.email).isEqualTo(email)
        assertThat(member.team!!.name).isEqualTo(team.name)
    }

    @Test
    @DisplayName("Member의 이름과 이메일을 수정한다.")
    fun changeMember() {
        // given
        val username = "hyoseok"
        val email = "test@gmail.com"
        val team = Team("teamA")
        val member = Member(username, email, team)

        // when
        member.change("jang", "test1234@gmail.com")

        // then
        assertThat(member.username).isEqualTo("jang")
        assertThat(member.email).isEqualTo("test1234@gmail.com")
    }

    @Test
    @DisplayName("Member의 Team을 수정한다.")
    fun changeTeamOfMember() {
        // given
        val teamA = Team("teamA")
        val teamB = Team("teamB")

        val username = "hyoseok"
        val email = "test@gmail.com"
        val member = Member(username, email, teamA)

        // when
        member.changeTeam(teamB)

        // then
        assertThat(member.team!!.name).isEqualTo(teamB.name)
    }

    @Test
    @DisplayName("Member의 Role을 수정한다.")
    fun changeRoleOfMember() {
        // given
        val username = "hyoseok"
        val email = "test@gmail.com"
        val team = Team("team")
        val member = Member(username, email, team)

        // when
        member.changeRole(Role.ADMIN)

        // then
        assertThat(member.role).isEqualTo(Role.ADMIN)
    }

}
