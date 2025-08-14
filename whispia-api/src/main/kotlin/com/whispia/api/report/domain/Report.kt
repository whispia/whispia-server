package com.whispia.api.report.domain

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
@Table(name = "reports")
class Report (

    val targetId: Long,
    @Enumerated(EnumType.STRING)
    val type: ReportType,
    val reason: String,
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
