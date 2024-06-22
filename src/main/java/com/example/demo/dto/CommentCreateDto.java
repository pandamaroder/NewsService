package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommentCreateDto {

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 300, message = "Допустимая длина от 1 до 30 символов")
    @Schema(name = "title", description = "Текст")
    private String text;

    @NotBlank
    @Schema(name = "userId", description = "userId")
    private long userId;

    @NotBlank
    @Schema(name = "newsId", description = "newsId")
    private long newsId;
}
