package com.whispia.api.chat.message.domain

import com.whispia.api.chat.chatroom.domain.ChatRoom
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
@DisplayName("ChatMessage JPA 테스트")
class ChatMessageTest(
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
    fun `ChatMessage를 저장하고 조회할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val chatRoom = createAndSaveChatRoom(user)
        val chatMessage = ChatMessage(
            content = "테스트 메시지",
            status = ChatMessageStatus.SENT,
            user = user,
            chatRoom = chatRoom
        )

        // when
        val savedMessage = entityManager.persistAndFlush(chatMessage)
        entityManager.refresh(savedMessage)

        // then
        assertThat(savedMessage).isNotNull
        assertThat(savedMessage.content).isEqualTo("테스트 메시지")
        assertThat(savedMessage.status).isEqualTo(ChatMessageStatus.SENT)
        assertThat(savedMessage.user.id).isEqualTo(user.id)
        assertThat(savedMessage.chatRoom.id).isEqualTo(chatRoom.id)
        assertThat(savedMessage.createdAt).isNotNull
        assertThat(savedMessage.updatedAt).isNotNull
    }

    @Test
    fun `메시지 상태를 변경할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val chatRoom = createAndSaveChatRoom(user)
        val chatMessage = ChatMessage(
            content = "상태 변경 테스트",
            status = ChatMessageStatus.SENT,
            user = user,
            chatRoom = chatRoom
        )
        val savedMessage = entityManager.persistAndFlush(chatMessage)

        // when
        savedMessage.status = ChatMessageStatus.DELIVERED
        entityManager.flush()
        entityManager.refresh(savedMessage)

        // then
        assertThat(savedMessage.status).isEqualTo(ChatMessageStatus.DELIVERED)
    }

    @Test
    fun `연관관계가 올바르게 저장된다`() {
        // given
        val user = createAndSaveUser()
        val chatRoom = createAndSaveChatRoom(user)
        val chatMessage = ChatMessage(
            content = "연관관계 테스트",
            status = ChatMessageStatus.SENT,
            user = user,
            chatRoom = chatRoom
        )

        // when
        val savedMessage = entityManager.persistAndFlush(chatMessage)

        // then
        assertThat(savedMessage.user).isEqualTo(user)
        assertThat(savedMessage.chatRoom).isEqualTo(chatRoom)
    }

    private fun createAndSaveUser(): User {
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "test@example.com",
            password = "password123",
            nickname = "testuser"
        )
        return entityManager.persistAndFlush(user)
    }

    private fun createAndSaveChatRoom(user: User): ChatRoom {
        val worry = Worry(
            title = "테스트 고민",
            content = "테스트 내용",
            category = WorryCategory.RELATIONSHIP,
            status = WorryStatus.ACTIVE,
            user = user
        )
        val savedWorry = entityManager.persistAndFlush(worry)

        val chatRoom = ChatRoom(worry = savedWorry)
        return entityManager.persistAndFlush(chatRoom)
    }
}
