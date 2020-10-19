package com.hyoseok.kotlinjpa.entity

enum class Role(val key: String, val value: String) {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자")
}
