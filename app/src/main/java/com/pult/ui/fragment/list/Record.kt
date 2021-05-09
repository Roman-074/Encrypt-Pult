package com.pult.ui.fragment.list

class Record(
        val id: Long,
        val name: String,
        val title: String,
        val body: String
) {
    override fun toString() = name
}