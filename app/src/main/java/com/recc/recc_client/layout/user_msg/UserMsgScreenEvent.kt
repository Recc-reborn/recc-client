package com.recc.recc_client.layout.user_msg

sealed class UserMsgScreenEvent {
    object NoConnection: UserMsgScreenEvent()
    data class PrintMessage(val msg: String): UserMsgScreenEvent()
}
