package com.spotify.app.service;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.SongResponseDTO;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.model.Song;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository ;

    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new UserException("Song not found")) ;
    }

    public SongResponseDTO getById(Long songId) {
        Song song = songRepository.findByIdCustom(songId).orElseThrow() ;
        return SongResponseMapper.INSTANCE.songToSongDTO(song);
    }


    public void saveSongAudio(MultipartFile multipartFile, Long songId) throws IOException {
        Song song = this.get(songId);

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            song.setAudio(fileName);

            String uploadDir = "song-audios/" + songId;

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (song.getAudio().isEmpty()) song.setAudio(null);
        }
        songRepository.save(song);
    }
    public void saveSongImage(MultipartFile image, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new UserException("Song not found"));
        if(image != null) {
            try {
                song.setImage(image.getBytes());
            } catch (IOException e) {
                throw new UserException(e.getMessage());
            }
        }
        songRepository.save(song);
    }
}
