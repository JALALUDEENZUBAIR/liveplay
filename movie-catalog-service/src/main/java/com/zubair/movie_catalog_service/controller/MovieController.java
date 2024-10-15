package com.zubair.movie_catalog_service.controller;

import com.zubair.movie_catalog_service.model.MovieInfo;
import com.zubair.movie_catalog_service.repository.MovieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    private static final String VIDEO_DIRECTORY = "C:\\Users\\jalal\\Desktop\\videostreaming\\video\\";
    @PostMapping("/movie/save-movie")
    public String saveMovie(@RequestParam("file") MultipartFile file, @RequestParam("title") String movieinfoTitle , @RequestParam("description") String movieInfoDescription ) throws IOException {
        File directory = new File(VIDEO_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(VIDEO_DIRECTORY, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        MovieInfo movie = new MovieInfo();
        movie.setTitle(movieinfoTitle);
        movie.setDescription(movieInfoDescription);
        movie.setPath(file.getOriginalFilename());
        movieRepository.save(movie);
        return "Saved Successfully";
    }
    @GetMapping("/movie/get-movies")
    public List<MovieInfo> getMovies() {
        return movieRepository.findAll();
    }
    @GetMapping("/movie/get-path/{movieID}")
    @CircuitBreaker(name = "movie",fallbackMethod = "fallbackRandomActivity")
    public String getPath(@PathVariable Integer movieID) {
        var temp=movieRepository.findById(movieID);
        return temp.map(MovieInfo::getPath).orElse(null);
    }
    public String fallbackRandomActivity(Throwable throwable) {
        return "hello";
    }
}