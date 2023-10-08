package com.spotify.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SongRequest (@NotBlank
                           @Size(max = 50, message = "name of song must not be greater than 50 char")
                           String name,

                           @NotBlank
                           String genre,

                           @Min(value = 1,message = "duration of song must greater than 0")
                           int duration,

                           @NotBlank
                           String lyric,

                           @NotEmpty
                           Long[] usersId

) {
}
