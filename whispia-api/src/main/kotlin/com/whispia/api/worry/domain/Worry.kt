package com.whispia.api.worry.domain

import com.whispia.api.user.domain.User
import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.*

/**
 * @author Seungwon-Choi
 */
@Entity
@Table(name = "worries")
class Worry (

    var title: String,
    var content: String,
    @Enumerated(EnumType.STRING)
    var category: WorryCategory,
    @Enumerated(EnumType.STRING)
    var status: WorryStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    val user: User

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var id: Long? = null
        private set
}
