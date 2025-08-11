package com.whispia.api.chatroom.domain

import com.whispia.api.worry.domain.Worry
import com.whispia.common.global.domain.BaseEntity
import jakarta.persistence.*

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
