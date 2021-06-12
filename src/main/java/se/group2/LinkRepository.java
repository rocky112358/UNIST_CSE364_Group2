package se.group2;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LinkRepository extends MongoRepository<Link, String> {
    public Link findByMovieId(int id);
}
