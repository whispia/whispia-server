package com.whispia.api.worry.domain

import com.whispia.api.user.domain.User
import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

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
