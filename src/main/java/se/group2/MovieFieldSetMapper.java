package se.group2;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MovieFieldSetMapper implements FieldSetMapper {
    @Override
    public Movie mapFieldSet(FieldSet fieldSet) {
        return new Movie(Integer.parseInt(fieldSet.readString(0)), fieldSet.readString(1), Arrays.stream(fieldSet.readString(2).split("\\|")).map(String::toLowerCase).collect(Collectors.toList()));
    }
}
