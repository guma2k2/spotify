package com.spotify.app.mapper;


import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);
    SongDTO songToSongDTO(Song song);

    List<SongDTO> songsToSongsDTO(List<Song> songs) ;

}
