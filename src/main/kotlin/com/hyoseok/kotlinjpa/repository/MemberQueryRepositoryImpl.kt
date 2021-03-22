package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.QMember.*
import com.hyoseok.kotlinjpa.service.dto.FindMemberDto
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.util.CollectionUtils
import java.util.*

@Repository
class MemberQueryRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) : MemberQueryRepository {
    override fun findWithFetchJoinById(id: Long): Member? {
        return jpaQueryFactory
                .selectFrom(member)
                .join(member.team).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne()
    }

    override fun paginationNoOffset(id: Long, pageSize: Long): List<Member> {
        return jpaQueryFactory
                .selectFrom(member)
                .join(member.team).fetchJoin()
                .where(ltMemberId(id))
                .orderBy(member.id.desc())
                .limit(pageSize)
                .fetch()
    }

    private fun ltMemberId(memberId: Long): BooleanExpression? {
        if (memberId == 0L) return null
        return member.id.lt(memberId)
    }

    override fun paginationCoveringIndex(pageNo: Long, pageSize: Long): List<FindMemberDto> {
        val memberIds: List<Long> = jpaQueryFactory
                .select(member.id)
                .from(member)
                .orderBy(member.id.desc())
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .fetch()

        if (CollectionUtils.isEmpty(memberIds)) return ArrayList()

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                FindMemberDto::class.java,
                                member.id, member.username, member.email, member.team.name
                        )
                )
                .from(member)
                .join(member.team)
                .where(member.id.`in`(memberIds))
                .orderBy(member.id.desc())
                .fetch()
    }
}
