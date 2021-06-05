package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    public List<Rating> findAll();
    public Rating findRatingByUserIdAndMovieId(int userId, int movieId);
}
