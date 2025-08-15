package com.whispia.api.report.domain

import com.whispia.api.user.domain.User
import com.whispia.api.user.domain.UserStatus
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
@DisplayName("Report JPA 테스트")
class ReportTest(
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
    fun `Report를 저장하고 조회할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val report = Report(
            type = ReportType.WORRY,
            targetId = 1L,
            reason = "부적절한 내용",
            user = user
        )

        // when
        val savedReport = entityManager.persistAndFlush(report)
        entityManager.refresh(savedReport)

        // then
        assertThat(savedReport).isNotNull
        assertThat(savedReport.type).isEqualTo(ReportType.WORRY)
        assertThat(savedReport.targetId).isEqualTo(1L)
        assertThat(savedReport.reason).isEqualTo("부적절한 내용")
        assertThat(savedReport.user.id).isEqualTo(user.id)
        assertThat(savedReport.createdAt).isNotNull
        assertThat(savedReport.updatedAt).isNotNull
    }

    @Test
    fun `채팅 신고를 저장할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val report = Report(
            type = ReportType.CHAT,
            targetId = 5L,
            reason = "욕설 사용",
            user = user
        )

        // when
        val savedReport = entityManager.persistAndFlush(report)

        // then
        assertThat(savedReport.type).isEqualTo(ReportType.CHAT)
        assertThat(savedReport.targetId).isEqualTo(5L)
        assertThat(savedReport.reason).isEqualTo("욕설 사용")
    }

    @Test
    fun `연관관계가 올바르게 저장된다`() {
        // given
        val user = createAndSaveUser()
        val report = Report(
            type = ReportType.WORRY,
            targetId = 3L,
            reason = "스팸",
            user = user
        )

        // when
        val savedReport = entityManager.persistAndFlush(report)

        // then
        assertThat(savedReport.user).isEqualTo(user)
        assertThat(savedReport.user.email).isEqualTo("report@example.com")
    }

    @Test
    fun `여러 신고 타입을 테스트할 수 있다`() {
        // given
        val user = createAndSaveUser()
        val worryReport = Report(
            type = ReportType.WORRY,
            targetId = 1L,
            reason = "고민 신고",
            user = user
        )
        val chatReport = Report(
            type = ReportType.CHAT,
            targetId = 2L,
            reason = "채팅 신고",
            user = user
        )

        // when
        val savedWorryReport = entityManager.persistAndFlush(worryReport)
        val savedChatReport = entityManager.persistAndFlush(chatReport)

        // then
        assertThat(savedWorryReport.type).isEqualTo(ReportType.WORRY)
        assertThat(savedChatReport.type).isEqualTo(ReportType.CHAT)
        assertThat(savedWorryReport.user.id).isEqualTo(savedChatReport.user.id)
    }

    private fun createAndSaveUser(): User {
        val user = User(
            key = UUID.randomUUID(),
            status = UserStatus.ACTIVE,
            email = "report@example.com",
            password = "password123",
            nickname = "reportuser"
        )
        return entityManager.persistAndFlush(user)
    }
}
