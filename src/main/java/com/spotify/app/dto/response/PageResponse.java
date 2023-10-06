package com.spotify.app.dto.response;

import java.util.List;

public record PageResponse <T> (int totalPage, int currentPage, String sortDir, String sortField, List<T> content) {
}
