package com.hyoseok.kotlinjpa.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
@Table(name = "member")
class Member : BaseTimeEntity() {

    /*
    * 중간에 보면, protected 접근 제한자를 지정했음
    * -> 가장 먼저 외부에서 엔티티의 필드 값을 변경하지 못하도록 하기 위함 (엔티티 클래스 내부에서만 변경 가능)
    * -> private를 사용하지 않은 이유? open class로 처리되어 있기 때문에 private로 세팅하면 컴파일 에러가 발생함
    * */

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

    /*
    * 생성자용 static 메소드를 대신하기 위해서 작성함.
    * -> 생성할 때 뭔가 비즈니스 로직이나 의미있는 생성자 메서드 명이 필요했기 때문에 사용함
    * */
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

    /* 연관 관계 편의 메소드 */
    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    /* 비즈니스 로직 */
    fun change(username: String, email: String) {
        this.username = username
        this.email = email
    }

    fun changeRole(role: Role) {
        this.role = role
    }

}
