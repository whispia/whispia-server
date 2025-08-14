package com.whispia.api.chatmessage.domain

import com.whispia.api.chatroom.domain.ChatRoom
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

@Entity
@Table(name = "chat_messages")
class ChatMessage (

    val content: String,
    @Enumerated(EnumType.STRING)
    var status: ChatMessageStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_idx")
    val chatRoom: ChatRoom

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var id: Long? = null
        private set
}
