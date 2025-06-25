package com.flasska.yndurfu.presentation.list

internal sealed interface ListScreenEvent {

    data class DeleteItem(val id: String) : ListScreenEvent
}
