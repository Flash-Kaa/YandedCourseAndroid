package com.flasska.yndurfu.domain.entity

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

fun JSONObject.parseToNote(): Note? {
    if (
        anyIsNull(
            ParseData.entries
                .filter { it != ParseData.DateTime }
                .map { it.toString() }
        )
    ) {
        return null
    }

    return try {
        Note(
            title = getString(ParseData.Title.name),
            content = getString(ParseData.Content.name),
            color = getInt(ParseData.Color.name),
            uid = getString(ParseData.Uid.name),
            important = Important.valueOf(getString(ParseData.Important.name)),
            deleteDateTime = if (isNull(ParseData.DateTime.name)) null
                else LocalDateTime.parse(getString(ParseData.DateTime.name), dtFormatter)
        )
    } catch (e: Exception) {
        null
    }
}

fun Note.parseToJson() = JSONObject().apply {
    put(ParseData.Color.name, color)
    put(ParseData.Important.name, important.name)
    put(ParseData.Title.name, title)
    put(ParseData.Content.name, content)
    put(ParseData.Uid.name, uid)
    deleteDateTime?.let {
        put(ParseData.DateTime.name, dtFormatter.format(deleteDateTime))
    }
}

private enum class ParseData {
    Important,
    Color,
    Title,
    Content,
    Uid,
    DateTime,
}

private fun JSONObject.anyIsNull(keys: List<String>) = keys.any { isNull(it) }
