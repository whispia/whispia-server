package com.whispia.api.worry.domain

import com.whispia.api.global.BaseTestContainer
import com.whispia.api.user.domain.User
import com.whispia.api.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Worry JPA 테스트")
class WorryTest(
    private val entityManager: TestEntityManager
) : BaseTestContainer() {

    @Test
    fun `Worry를 저장하고 조회할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worry = Worry(
            title = "테스트 고민",
            content = "고민 내용입니다",
            category = WorryCategory.RELATIONSHIP,
            status = WorryStatus.ACTIVE,
            user = user
        )

        // when
        val savedWorry = entityManager.persistAndFlush(worry)
        entityManager.refresh(savedWorry)

        // then
        assertThat(savedWorry).isNotNull
        assertThat(savedWorry.title).isEqualTo("테스트 고민")
        assertThat(savedWorry.content).isEqualTo("고민 내용입니다")
        assertThat(savedWorry.category).isEqualTo(WorryCategory.RELATIONSHIP)
        assertThat(savedWorry.status).isEqualTo(WorryStatus.ACTIVE)
        assertThat(savedWorry.user.id).isEqualTo(user.id)
        assertThat(savedWorry.createdAt).isNotNull
        assertThat(savedWorry.updatedAt).isNotNull
    }

    @Test
    fun `고민 상태를 변경할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worry = Worry(
            title = "상태 변경 테스트",
            content = "내용",
            category = WorryCategory.CAREER,
            status = WorryStatus.ACTIVE,
            user = user
        )
        val savedWorry = entityManager.persistAndFlush(worry)

        // when
        savedWorry.status = WorryStatus.INACTIVE
        entityManager.flush()
        entityManager.refresh(savedWorry)

        // then
        assertThat(savedWorry.status).isEqualTo(WorryStatus.INACTIVE)
    }

    @Test
    fun `고민 내용을 수정할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worry = Worry(
            title = "원본 제목",
            content = "원본 내용",
            category = WorryCategory.STUDY,
            status = WorryStatus.ACTIVE,
            user = user
        )
        val savedWorry = entityManager.persistAndFlush(worry)

        // when
        savedWorry.title = "수정된 제목"
        savedWorry.content = "수정된 내용"
        savedWorry.category = WorryCategory.RELATIONSHIP
        entityManager.flush()
        entityManager.refresh(savedWorry)

        // then
        assertThat(savedWorry.title).isEqualTo("수정된 제목")
        assertThat(savedWorry.content).isEqualTo("수정된 내용")
        assertThat(savedWorry.category).isEqualTo(WorryCategory.RELATIONSHIP)
    }

    @Test
    fun `연관관계가 올바르게 저장된다`() {
        // given
        val user = createAndSaveUser()
        val worry = Worry(
            title = "연관관계 테스트",
            content = "내용",
            category = WorryCategory.HEALTH,
            status = WorryStatus.ACTIVE,
            user = user
        )

        // when
        val savedWorry = entityManager.persistAndFlush(worry)

        // then
        assertThat(savedWorry.user).isEqualTo(user)
    }

    private fun createAndSaveUser(): User {
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "worry@example.com",
            password = "password123",
            nickname = "worryuser"
        )
        return entityManager.persistAndFlush(user)
    }
}
