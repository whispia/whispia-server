package com.whispia.api.report.domain

import com.whispia.api.user.domain.User
import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.*

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
