package se.group2;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class UserFieldSetMapper implements FieldSetMapper {
    @Override
    public User mapFieldSet(FieldSet fieldSet) {
        return new User(Integer.parseInt(fieldSet.readString(0)), fieldSet.readString(1), Integer.parseInt(fieldSet.readString(2)), Integer.parseInt(fieldSet.readString(3)), fieldSet.readString(4));
    }
}
