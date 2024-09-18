package gdsc.session.question.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@Valid
public record SearchDto (
        @Nullable
        String subject
){
}
