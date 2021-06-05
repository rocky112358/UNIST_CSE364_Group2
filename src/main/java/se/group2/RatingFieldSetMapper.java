package se.group2;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class RatingFieldSetMapper implements FieldSetMapper {
    @Override
    public Rating mapFieldSet(FieldSet fieldSet) {
        return new Rating(Integer.parseInt(fieldSet.readString(0)), Integer.parseInt(fieldSet.readString(1)), Integer.parseInt(fieldSet.readString(2)), Integer.parseInt(fieldSet.readString(3)));
    }
}
