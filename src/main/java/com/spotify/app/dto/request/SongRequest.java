package com.spotify.app.dto.request;

import jakarta.validation.constraints.*;

public record SongRequest (@NotBlank
                           @Size(max = 50, message = "name of song must not be greater than 50 char")
                           String name,

                           @NotBlank
                           String genre,

                           @Min(value = 1, message = "duration of song must greater than 0 second")
                           int duration,
                           @NotBlank
                           String lyric,
                           int day,
                           int month,
                           int year,
                           @NotBlank
                           String label,
                           Long usersId

) {
}
