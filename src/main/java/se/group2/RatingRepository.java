package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    public Rating findRatingByUserIdAndMovieId(int userId, int movieId);
}
