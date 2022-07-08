package com.aspire.moviecatalogservice.controller;

import com.aspire.moviecatalogservice.models.CatalogItem;
import com.aspire.moviecatalogservice.models.Movie;
import com.aspire.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratings/user/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
          /*  Movie movie = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
          */
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "A film written and directed by Christopher Nolan", rating.getRating());

        }).collect(Collectors.toList());


    }
}
