package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Schema(description = "Форма регистрации пользователя")
public class NewsCreateRequest {

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 30, message = "Допустимая длина от 1 до 30 символов")
    @Schema(name = "title", description = "Заголовок")
    private String title;

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 300, message = "Допустимая длина от 1 до 300 символов")
    @Schema(name = "content", description = "content")
    private String content;

    @NotBlank
    @Schema(name = "userId", description = "userId")
    private long userId;

    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 30, message = "Допустимая длина от 1 до 30 символов")
    @Schema(name = "categoryName", description = "categoryName")
    private String categoryName;
}
