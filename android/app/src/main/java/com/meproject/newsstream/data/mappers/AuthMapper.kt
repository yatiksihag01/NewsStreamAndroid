package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.remote.dto.auth.login.LoginDetailsDto
import com.meproject.newsstream.domain.model.LoginDetails

fun LoginDetails.toLoginDto(): LoginDetailsDto {
    return LoginDetailsDto(
        email = email,
        password = password
    )
}