package com.whispia.api.user.domain

import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.*
import java.util.*

/**
 * @author Seungwon-Choi
 */
@Entity
@Table(name = "users")
class User (

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
}
