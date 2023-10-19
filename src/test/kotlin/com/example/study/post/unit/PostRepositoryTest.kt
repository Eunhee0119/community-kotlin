package com.example.study.post.unit

import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import com.example.study.member.repository.MemberRepository
import com.example.study.post.application.dto.PostCondition
import com.example.study.post.domain.Post
import com.example.study.post.repository.PostRepository
import com.example.study.util.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@DataJpaTest
@Import(TestConfig::class)
class PostRepositoryTest {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    var 회원: Member? = null

    @BeforeEach
    fun setUp() {
        val email = "test@email.com"
        val password = "password11@@"
        val nickname = "테스트계정"
        val name = "test"
        val age = 20
        val birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val gender = Gender.WOMAN
        val member = Member(null, email, password, nickname, name, age, birthDate, gender)
        memberRepository.save(member)
        회원 = member
    }

    @Test
    private fun savePost() {
        val post = Post(null, "제목", "내용")
        postRepository.save(post)
    }

    @DisplayName("게시글을 조회한다.")
    @Test
    fun getPost() {
        // given
        val post = Post(null, "제목", "내용", 회원)
        postRepository.save(post)
        val findPost = postRepository.findById(post.id!!).get()

        assertThat(post).isSameAs(findPost)
    }


    @DisplayName("게시글을 수정한다.")
    @Test
    fun updatePost() {
        // given
        val post = Post(null, "제목", "내용", 회원)
        postRepository.save(post)

        val title = "변경 후 제목"
        val content = "변경 후 내용"
        post.title = title
        post.content = content
        postRepository.save(post)

        val findPost = postRepository.findById(post.id!!).get()
        assertThat(findPost.title).isEqualTo(title)
        assertThat(findPost.content).isEqualTo(content)
    }

    @DisplayName("게시글 목록을 조회한다.")
    @Test
    fun getPostList() {
        // given
        val totalCount = 10L
        val pageSize = 5
        for (i in 0 until totalCount) {
            val post = Post(null, "제목$i", "내용$i", 회원)
            postRepository.save(post)
        }

        val cond = PostCondition(0, pageSize)
        val postsPage = postRepository.findAllByCondition(cond)

        assertThat(postsPage.totalPages).isEqualTo(totalCount / pageSize)
        assertThat(postsPage.totalElements).isEqualTo(totalCount)
        assertThat(postsPage.content[0].title).isEqualTo("제목${totalCount - 1}")
    }


    @DisplayName("게시글 목록을 조회한다. - 제목으로 검색할 경우")
    @Test
    fun getPostListWithSearchTitle() {
        // given
        val totalCount = 10L
        val pageSize = 5
        for (i in 0 until totalCount) {
            val post = Post(null, "제목$i", "내용$i", 회원)
            postRepository.save(post)
        }

        val searchTitle = "제목1"
        val cond = PostCondition(0, pageSize, searchTitle)
        val postsPage = postRepository.findAllByCondition(cond)

        assertThat(postsPage.totalPages).isEqualTo(1)
        assertThat(postsPage.totalElements).isEqualTo(1)
        assertThat(postsPage.content[0].title).isEqualTo(searchTitle)
    }

    @DisplayName("게시글 목록을 조회한다. - 닉네임으로 검색할 경우")
    @Test
    fun getPostListWithSearchNickName() {
        // given
        val birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val 회원2 = Member(null, "test22@email.com", "password11@@", "회원2", "test22", 20, birthDate, Gender.WOMAN)
        memberRepository.save(회원2)

        val totalCount = 10L
        val pageSize = 5
        for (i in 0 until totalCount) {
            val post = Post(null, "제목$i", "내용$i", 회원2)
            postRepository.save(post)
        }

        postRepository.save(Post(null, "게시글 목록조회 테스트", "내용입니다", 회원))

        val cond = PostCondition(0, pageSize, null, 회원!!.nickname)
        val postsPage = postRepository.findAllByCondition(cond)

        assertThat(postsPage.totalPages).isEqualTo(1)
        assertThat(postsPage.totalElements).isEqualTo(1)
        assertThat(postsPage.content[0].nickname).isEqualTo(회원!!.nickname)
    }

}