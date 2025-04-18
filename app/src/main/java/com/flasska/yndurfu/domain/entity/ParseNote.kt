package com.flasska.yndurfu.domain.entity

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

fun JSONObject.parseToNote(): Note? {
    if (
        isNull(ParseData.Title.name)
        || isNull(ParseData.Content.name)
        || isNull(ParseData.Uid.name)
        || isNull(ParseData.DateTime.name)
        || isNull(ParseData.Color.name)
        || isNull(ParseData.Important.name)
    ) {
        return null
    }

    return Note(
        title = getString(ParseData.Title.name),
        content = getString(ParseData.Content.name),
        color = getInt(ParseData.Color.name),
        uid = UUID.fromString(getString(ParseData.Uid.name)),
        important = Important.valueOf(getString(ParseData.Important.name)),
        deleteDateTime = LocalDateTime.parse(getString(ParseData.DateTime.name), dtFormatter)
    )
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
    DateTime
}
