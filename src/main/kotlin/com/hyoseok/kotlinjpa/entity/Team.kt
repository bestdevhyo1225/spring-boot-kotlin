package com.hyoseok.kotlinjpa.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
class Team(name: String): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    val id: Long? = null

    @Column(nullable = false)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "team")
    var members: MutableList<Member> = ArrayList()

    fun change(name: String) {
        this.name = name
    }

}
