package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    public List<Movie> findAll();
    public Movie findById(int id);
    public List<Movie> findByGenre(String genre);
    public Movie findByTitle(String title);
}
