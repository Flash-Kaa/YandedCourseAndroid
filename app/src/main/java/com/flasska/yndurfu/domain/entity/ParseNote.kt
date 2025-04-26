package com.flasska.yndurfu.domain.entity

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

fun JSONObject.parseToNote(): Note? {
    if (ParseData.anyIsNullInJson(this)) {
        return null
    }

    return try {
        Note(
            title = getString(ParseData.Title.name),
            content = getString(ParseData.Content.name),
            color = getInt(ParseData.Color.name),
            uid = UUID.fromString(getString(ParseData.Uid.name)),
            important = Important.valueOf(getString(ParseData.Important.name)),
            deleteDateTime = LocalDateTime.parse(getString(ParseData.DateTime.name), dtFormatter)
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
    put(ParseData.Uid.name, uid.toString())
    put(ParseData.DateTime.name, dtFormatter.format(deleteDateTime))
}

private enum class ParseData {
    Important,
    Color,
    Title,
    Content,
    Uid,
    DateTime;

    companion object {
        fun anyIsNullInJson(json: JSONObject) = entries.map { it.name }.any(json::isNull)
    }
}

