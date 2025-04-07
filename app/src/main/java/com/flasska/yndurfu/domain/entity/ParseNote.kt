package com.flasska.yndurfu.domain.entity

import android.graphics.Color
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

fun JSONObject.parseToNote(): Note? {
    val colorARGB = if (isNull(ParseData.Color.name)) {
        Color.WHITE
    } else {
        getInt(ParseData.Color.name)
    }

    val important = if (isNull(ParseData.Important.name)) {
        Important.Common.name
    } else {
        getString(ParseData.Important.name)
    }

    if (
        isNull(ParseData.Title.name)
        || isNull(ParseData.Content.name)
        || isNull(ParseData.Uid.name)
        || isNull(ParseData.DateTime.name)
    ) {
        return null
    }

    return Note(
        title = getString(ParseData.Title.name),
        content = getString(ParseData.Content.name),
        color = Color.valueOf(colorARGB),
        uid = UUID.fromString(getString(ParseData.Uid.name)),
        important = Important.valueOf(important),
        deleteDateTime = LocalDateTime.parse(getString(ParseData.DateTime.name), dtFormatter)
            .plusMonths(1)
    )
}

fun Note.parseToJson() = JSONObject().apply {
    if (color != Color.valueOf(Color.WHITE)) {
        put(ParseData.Color.name, color.toArgb())
    }

    if (important != Important.Common) {
        put(ParseData.Important.name, important.name)
    }

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