package com.example.study.post.repository

import com.example.study.post.application.dto.PostCondition
import com.example.study.post.application.dto.PostSimpleDto
import com.example.study.post.domain.Post
import com.example.study.post.domain.QPost.post
import com.querydsl.core.types.Projections.constructor
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils.hasText


@Suppress("DEPRECATION")
@Transactional(readOnly = true)
class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom, QuerydslRepositorySupport(Post::class.java) {

    override fun findAllByCondition(cond: PostCondition): Page<PostSimpleDto> {
        val pageable: Pageable = PageRequest.of(cond.page!!, cond.size!!)
        val query = jpaQueryFactory
            .select(
                constructor(
                    PostSimpleDto::class.java,
                    post.id,
                    post.title,
                    post.member.nickname,
                    post.createdAt
                )
            )
            .from(post)
            .join(post.member)
            .where(
                titleLike(cond.title),
                nicknameLike(cond.nickname)
            )
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
        val totalCount = query.fetchCount()
        val results: List<PostSimpleDto> = querydsl!!.applyPagination(pageable, query).fetch()

        return PageImpl(results, pageable, totalCount)
    }


    private fun titleLike(title: String?): BooleanExpression? {
        return if (hasText(title)) post.title.like("%$title%") else null
    }


    private fun nicknameLike(nickname: String?): BooleanExpression? {
        return if (hasText(nickname)) post.member.nickname.like("%$nickname%") else null
    }
}