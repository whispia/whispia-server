package com.whispia.api.user.domain

import com.whispia.common.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/**
 * @author Seungwon-Choi
 */
@Entity
@Table(name = "users")
class User private constructor(
    val uuid: UUID,

    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    val email: String,

    var password: String,

    var nickname: String

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var id: Long? = null
        private set

    protected constructor() : this(
        UUID.randomUUID(),
        UserStatus.ACTIVE,
        "",
        "",
        ""
    )

    // 정적 팩토리 메서드
    companion object {
        fun create(
            email: String,
            password: String,
            nickname: String,
            status: UserStatus = UserStatus.ACTIVE
        ): User {
            return User(
                uuid = UUID.randomUUID(),
                status = status,
                email = email,
                password = password,
                nickname = nickname
            )
        }
    }
}