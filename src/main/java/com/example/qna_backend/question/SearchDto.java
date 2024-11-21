package com.example.qna_backend.question;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@Valid
public record SearchDto (
        @Nullable
        String subject
){
}

