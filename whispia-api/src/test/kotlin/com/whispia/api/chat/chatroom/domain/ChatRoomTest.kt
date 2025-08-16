package com.whispia.api.chat.chatroom.domain

import com.whispia.api.user.domain.User
import com.whispia.api.user.domain.UserStatus
import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableJpaAuditing
@Transactional
@Testcontainers
@ActiveProfiles("test")
@DisplayName("ChatRoom JPA 테스트")
class ChatRoomTest(
    private val entityManager: TestEntityManager
) {

    companion object {
        @Container
        @JvmStatic
        val postgres = PostgreSQLContainer("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Test
    fun `ChatRoom을 저장하고 조회할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worry = createAndSaveWorry(user)
        val chatRoom = ChatRoom(worry = worry)

        // when
        val savedChatRoom = entityManager.persistAndFlush(chatRoom)
        entityManager.refresh(savedChatRoom)

        // then
        assertThat(savedChatRoom).isNotNull
        assertThat(savedChatRoom.worry.id).isEqualTo(worry.id)
        assertThat(savedChatRoom.createdAt).isNotNull
        assertThat(savedChatRoom.updatedAt).isNotNull
    }

    @Test
    fun `연관관계가 올바르게 저장된다`() {
        // given
        val user = createAndSaveUser()
        val worry = createAndSaveWorry(user)
        val chatRoom = ChatRoom(worry = worry)

        // when
        val savedChatRoom = entityManager.persistAndFlush(chatRoom)

        // then
        assertThat(savedChatRoom.worry).isEqualTo(worry)
        assertThat(savedChatRoom.worry.title).isEqualTo("테스트 고민")
    }

    @Test
    fun `Worry와 OneToOne 관계를 확인할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worry = createAndSaveWorry(user)
        val chatRoom = ChatRoom(worry = worry)

        // when
        val savedChatRoom = entityManager.persistAndFlush(chatRoom)

        // then
        assertThat(savedChatRoom.worry.user.id).isEqualTo(user.id)
        assertThat(savedChatRoom.worry.status).isEqualTo(WorryStatus.TEMP)
    }

    private fun createAndSaveUser(): User {
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "chatroom@example.com",
            password = "password123",
            nickname = "chatroomuser"
        )
        return entityManager.persistAndFlush(user)
    }

    private fun createAndSaveWorry(user: User): Worry {
        val worry = Worry(
            title = "테스트 고민",
            content = "채팅방 테스트용 고민",
            category = WorryCategory.RELATIONSHIP,
            status = WorryStatus.TEMP,
            user = user
        )
        return entityManager.persistAndFlush(worry)
    }
}
