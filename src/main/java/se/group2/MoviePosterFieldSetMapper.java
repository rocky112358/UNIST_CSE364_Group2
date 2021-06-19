package se.group2;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class MoviePosterFieldSetMapper implements FieldSetMapper {
    @Override
    public MoviePoster mapFieldSet(FieldSet fieldSet) {
        return new MoviePoster(Integer.parseInt(fieldSet.readString(0)), fieldSet.readString(1));
    }
}
