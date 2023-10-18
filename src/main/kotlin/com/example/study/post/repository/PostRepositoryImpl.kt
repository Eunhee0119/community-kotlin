package com.example.study.post.repository

import com.example.study.post.application.dto.PostCondition
import com.example.study.post.application.dto.PostSimpleDto
import com.example.study.post.domain.Post
import com.querydsl.core.QueryResults
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections.constructor
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom , QuerydslRepositorySupport(Post::class.java) {

    override fun search(condition: PostCondition): List<Post> {
       /* val queryResults: QueryResults<Post> = jpaQueryFactory
            .selectFrom()
            .orderBy(member.username.desc())
            .offset(1)
            .limit(2)
            .fetchResults()*/
        return ArrayList<Post>()
    }

//    private fun fetchAll(predicate: Predicate, pageable: Pageable): List<PostSimpleDto?>? { // 6
//        return querydsl!!.applyPagination(
//            pageable,
//            jpaQueryFactory
//                .select(
//                    constructor(
//                        PostSimpleDto::class.java,
//                        post.id,
//                        post.title,
//                        post.member.nickname,
//                        post.createdAt
//                    )
//                )
//                .from(post)
//                .join(post.member)
//                .where(predicate)
//                .orderBy(post.id.desc())
//        ).fetch()
//    }
}