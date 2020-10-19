package com.hyoseok.kotlinjpa.repository

import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.entity.QMember.*
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
        private val jpaQueryFactory: JPAQueryFactory
): MemberQueryRepository {
    override fun findWithFetchJoinById(id: Long): Member? {
        return jpaQueryFactory
                .selectFrom(member)
                .join(member.team).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne()
    }
}