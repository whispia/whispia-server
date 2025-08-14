package com.whispia.api.chatroom.domain

import com.whispia.api.worry.domain.Worry
import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

/**
 * @author Seungwon-Choi
 */
@Entity
@Table(name = "chat_rooms")
class ChatRoom (

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worry_idx")
    val worry: Worry

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var id: Long? = null
        private set
}
