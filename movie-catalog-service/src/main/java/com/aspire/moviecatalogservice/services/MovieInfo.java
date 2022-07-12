package com.aspire.moviecatalogservice.services;

import com.aspire.moviecatalogservice.models.CatalogItem;
import com.aspire.moviecatalogservice.models.Movie;
import com.aspire.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }
    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}

  /*
  ---Alternative WebClient way----
  Movie movie = webClientBuilder.build().get().uri("http://localhost:8081/movies/" + rating.getMovieId()).retrieve()
    .bodyToMono(Movie.class).block();
  */