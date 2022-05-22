package com.ssafy.ssafybottle_manager.data.dto.pane

data class PaneMenu(
    var id: Int,
    val name: String,
    var icon: Int?,
    var isSelected: Boolean
)