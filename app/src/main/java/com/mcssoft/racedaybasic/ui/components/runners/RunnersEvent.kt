package com.mcssoft.racedaybasic.ui.components.runners

import com.mcssoft.racedaybasic.domain.model.Runner

sealed class RunnersEvent {

    data class GetCheck(
        val checked: Boolean,
        val runner: Runner
    ): RunnersEvent()
}
