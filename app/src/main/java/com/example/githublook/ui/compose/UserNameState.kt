package com.example.githublook.ui.compose

class UserNameState(userName: String? = null) :
    TextFieldState(validator = ::isUserNameValid, errorFor = ::userNameValidationError) {
    init {
        userName?.let {
            text = it
        }
    }
}

private fun userNameValidationError(userName: String): String {
    return "Invalid username: $userName"
}

private fun isUserNameValid(userName: String): Boolean {
    return userName.isNotEmpty() && userName.isNotBlank()
}

val UserNameStateSaver = textFieldStateSaver(UserNameState())
