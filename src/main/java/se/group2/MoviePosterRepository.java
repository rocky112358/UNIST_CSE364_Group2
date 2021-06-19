package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoviePosterRepository extends MongoRepository<MoviePoster, String> {
    public MoviePoster findByMovieId(int id);
}
