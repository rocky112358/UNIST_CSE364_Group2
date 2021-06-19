package se.group2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@EnableBatchProcessing
@Configuration
public class CsvToMongoJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private MoviePosterRepository moviePosterRepository;

    @Bean
    public Job readCSVFile() {
        return jobBuilderFactory.get("readCSVFile")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Movie, Movie>chunk(10).reader(movieReader())
                .writer(movieWriter()).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<User, User>chunk(10).reader(userReader())
                .writer(userWriter()).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").<Rating, Rating>chunk(10).reader(ratingReader())
                .writer(ratingWriter()).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4").<Link, Link>chunk(10).reader(linkReader())
                .writer(linkWriter()).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5").<MoviePoster, MoviePoster>chunk(10).reader(moviePosterReader())
                .writer(moviePosterWriter()).build();
    }

    @Bean
    public FlatFileItemReader<Movie> movieReader() {
        FlatFileItemReader<Movie> reader = new FlatFileItemReader<>();
        MovieFieldSetMapper mapper = new MovieFieldSetMapper();
        reader.setResource(new ClassPathResource("movies.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id", "title", "genre");
            }});
            setFieldSetMapper(mapper);
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<User> userReader() {
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        UserFieldSetMapper mapper = new UserFieldSetMapper();
        reader.setResource(new ClassPathResource("users.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id", "gender", "age", "occupation", "zipcode");
            }});
            setFieldSetMapper(mapper);
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<Rating> ratingReader() {
        FlatFileItemReader<Rating> reader = new FlatFileItemReader<>();
        RatingFieldSetMapper mapper = new RatingFieldSetMapper();
        reader.setResource(new ClassPathResource("ratings.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("userId", "movieId", "rating", "timestamp");
            }});
            setFieldSetMapper(mapper);
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<Link> linkReader() {
        FlatFileItemReader<Link> reader = new FlatFileItemReader<>();
        LinkFieldSetMapper mapper = new LinkFieldSetMapper();
        reader.setResource(new ClassPathResource("links.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("movieId", "imdbId");
            }});
            setFieldSetMapper(mapper);
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<MoviePoster> moviePosterReader() {
        FlatFileItemReader<MoviePoster> reader = new FlatFileItemReader<>();
        MoviePosterFieldSetMapper mapper = new MoviePosterFieldSetMapper();
        reader.setResource(new ClassPathResource("movie_poster.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("movieId", "imageLink");
            }});
            setFieldSetMapper(mapper);
        }});
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Movie> movieWriter() {
        RepositoryItemWriter<Movie> writer = new RepositoryItemWriter<>();
        writer.setRepository(movieRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public RepositoryItemWriter<User> userWriter() {
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public RepositoryItemWriter<Rating> ratingWriter() {
        RepositoryItemWriter<Rating> writer = new RepositoryItemWriter<>();
        writer.setRepository(ratingRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public RepositoryItemWriter<Link> linkWriter() {
        RepositoryItemWriter<Link> writer = new RepositoryItemWriter<>();
        writer.setRepository(linkRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public RepositoryItemWriter<MoviePoster> moviePosterWriter() {
        RepositoryItemWriter<MoviePoster> writer = new RepositoryItemWriter<>();
        writer.setRepository(moviePosterRepository);
        writer.setMethodName("save");
        return writer;
    }
}
