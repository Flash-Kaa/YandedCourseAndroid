package com.flasska.yndurfu.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val important: Important,
    val deleteDateTime: LocalDateTime,
    val uid: UUID = UUID.randomUUID(),
) {
    override fun equals(other: Any?): Boolean {
        return (other as? Note)?.let { it.uid == uid } ?: false
    }

    override fun hashCode() = uid.hashCode()

    companion object
}
