package com.example.study.post.application.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import lombok.Data


@Data
class PostCondition (

    @NotNull(message = "페이지 번호를 입력해주세요.")
    @PositiveOrZero(message = "올바른 페이지 번호를 입력해주세요.")
    private val page: Int? = null,

    @NotNull(message = "페이지 사이즈를 입력해주세요.")
    @Positive(message = "올바른 페이지 사이즈를 입력해주세요.")
    private val size:  Int? = null

)