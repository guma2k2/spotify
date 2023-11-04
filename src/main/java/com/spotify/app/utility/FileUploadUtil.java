package com.spotify.app.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtil {

	public final static String baseUrlUserDefault = "https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-image-182145777.jpg";
	public final static String baseUrl = "http://localhost:8080" ;
	public final static String baseUrlFail = "https://cdn.tgdd.vn/hoi-dap/580732/loi-404-not-found-la-gi-9-cach-khac-phuc-loi-404-not-5-800x450.jpg";
	public final static String baseUrlImagePlaylistLikedSongs = "https://i.scdn.co/image/ab67706c0000da8470d229cb865e8d81cdce0889";
	public final static String baseUrlThumbnailPlaylistLikedSongs = "https://www.colorhexa.com/5534d6.png";
	public final static String baseUrlPlaylistImage = "https://community.spotify.com/t5/image/serverpage/image-id/25294i2836BD1C1A31BDF2?v=v2";
	public final static String baseUrlPlaylistThumbnail = "https://www.colorhexa.com/383838.png";
	public static void saveFile(String uploadDir, String fileName,
			MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("Could not save file: " + fileName, ex);
		}
	}
	
	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException ex) {
//						log.error("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException ex) {
//			log.error("Could not list directory: " + dirPath);
		}
	}
	
	public static void removeDir(String dir) {
		cleanDir(dir);
		try {
			Files.delete(Paths.get(dir));
		} catch (IOException e) {
//			log.error("Could not remove directory: " + dir);
		}
		
	}
}
