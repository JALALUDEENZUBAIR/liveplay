package com.zubair.movie_catalog_service.repository;

import com.zubair.movie_catalog_service.model.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieInfo,Integer> {
}
