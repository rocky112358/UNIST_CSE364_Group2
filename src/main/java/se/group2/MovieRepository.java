package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    public Movie findById(int id);
    public List<Movie> findByGenre(String genre);
    public List<Movie> findByTitle(String title);
}
