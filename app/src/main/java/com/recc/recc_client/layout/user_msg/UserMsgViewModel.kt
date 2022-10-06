package com.recc.recc_client.layout.user_msg

import com.recc.recc_client.layout.common.BaseEventViewModel

class UserMsgViewModel: BaseEventViewModel<UserMsgScreenEvent>() {

    fun postMessage(msg: String) {
        postEvent(UserMsgScreenEvent.PrintMessage(msg))
    }

    fun handleNoConnection() {
        postEvent(UserMsgScreenEvent.NoConnection)
    }
}