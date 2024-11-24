package com.suleekyuri.hositamtam.favorite.service;

import com.suleekyuri.hositamtam.favorite.dto.FavoriteDto;
import java.util.List;

public interface FavoriteService {

    List<FavoriteDto> getFavorites(Long userId);

    void addFavorite(Long userId, String aptId);

    Boolean isFavorite(Long userId, String aptId);

    void removeFavorite(Long userId, String aptId);
}
