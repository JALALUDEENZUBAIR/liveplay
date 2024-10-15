package com.zubair.movie_streaming_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieCatalogService {
    @Autowired
    private RestTemplate restTemplate;
    public String getMoviePath(Integer movieId) {
        var response=restTemplate.getForEntity("http://movie-catalog/movie/get-path/"+movieId, String.class);
        return response.getBody();
    }
}
