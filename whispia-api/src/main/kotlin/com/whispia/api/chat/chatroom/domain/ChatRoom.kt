package com.whispia.api.chat.chatroom.domain

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

@Entity
@Table(name = "chat_rooms")
class ChatRoom (

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worry_id")
    val worry: Worry

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
        private set
}
