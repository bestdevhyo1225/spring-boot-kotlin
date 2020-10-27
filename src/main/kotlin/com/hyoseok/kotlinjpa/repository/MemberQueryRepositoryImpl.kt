package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.QMember.*
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

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
}
