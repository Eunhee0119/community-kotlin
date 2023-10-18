package com.example.study.post.unit.fixture

import com.example.study.member.domain.Member
import com.example.study.post.domain.Post
import org.springframework.test.util.ReflectionTestUtils


class PostFixture {
    companion object {

        private var id = 1L

        fun 게시글_등록(title: String, content: String, member: Member?): Post {
            val post: Post = Post(null, title, content)
            ReflectionTestUtils.setField(post, "id", id++)
            ReflectionTestUtils.setField(post, "member", member)
            return post
        }
    }
}