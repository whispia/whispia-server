package com.whispia.api.user.domain

import com.whispia.api.global.BaseTestContainer
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
@DisplayName("User JPA 테스트")
class UserTest(
    private val entityManager: TestEntityManager
) : BaseTestContainer() {

    @Test
    fun `User를 저장하고 조회할 수 있다`() {
        // given
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "test@example.com",
            password = "password123",
            nickname = "testuser"
        )

        // when
        val savedUser = entityManager.persistAndFlush(user)
        entityManager.refresh(savedUser)

        // then
        assertThat(savedUser).isNotNull
        assertThat(savedUser.email).isEqualTo("test@example.com")
        assertThat(savedUser.nickname).isEqualTo("testuser")
        assertThat(savedUser.status).isEqualTo(UserStatus.ACTIVE)
        assertThat(savedUser.createdAt).isNotNull
        assertThat(savedUser.updatedAt).isNotNull
    }

    @Test
    fun `사용자 상태를 변경할 수 있다`() {
        // given
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "status@example.com",
            password = "password123",
            nickname = "statususer"
        )
        val savedUser = entityManager.persistAndFlush(user)

        // when
        savedUser.status = UserStatus.INACTIVE
        entityManager.flush()
        entityManager.refresh(savedUser)

        // then
        assertThat(savedUser.status).isEqualTo(UserStatus.INACTIVE)
    }

    @Test
    fun `닉네임과 비밀번호를 변경할 수 있다`() {
        // given
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "update@example.com",
            password = "oldpassword",
            nickname = "oldnick"
        )
        val savedUser = entityManager.persistAndFlush(user)

        // when
        savedUser.nickname = "newnick"
        savedUser.password = "newpassword"
        entityManager.flush()
        entityManager.refresh(savedUser)

        // then
        assertThat(savedUser.nickname).isEqualTo("newnick")
        assertThat(savedUser.password).isEqualTo("newpassword")
    }
}
