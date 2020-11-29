package com.hyoseok.kotlinjpa.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
@Table(name = "member")
class Member : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L

    @Column(nullable = false)
    lateinit var username: String
        protected set

    @Column(nullable = false)
    lateinit var email: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.USER
        protected set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "team_id")
    lateinit var team: Team
        protected set

    companion object {
        @JvmStatic
        fun create(username: String, email: String, team: Team): Member {
            val member = Member()

            member.username = username
            member.email = email
            /*
             * 양방향 연관관계가 설정되었을 경우, 양쪽에 값을 입력해야 하기 때문에
             * '연관 관계 편의 메소드'를 사용해서 앙쪽의 값을 입력하자
             * */
            member.changeTeam(team)

            return member
        }
    }

    // 연관 관계 편의 메소드
    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    fun change(username: String, email: String) {
        this.username = username
        this.email = email
    }

    fun changeRole(role: Role) {
        this.role = role
    }

}
