package com.whispia.api.chat.message.domain

enum class ChatMessageStatus {

    SENT,       // 내가 보낸 메시지가 서버에 성공적으로 전송됨
    DELIVERED,  // 상대방 디바이스(또는 계정)에 도착함
    SEEN,       // 상대방이 메시지를 읽음 (읽음 확인 켜져 있을 때만 표시)
    FAILED,     // 메시지 전송 실패 (네트워크 문제 등)
}
