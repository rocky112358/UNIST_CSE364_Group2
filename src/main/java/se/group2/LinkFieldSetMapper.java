package se.group2;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class LinkFieldSetMapper implements FieldSetMapper {
    @Override
    public Link mapFieldSet(FieldSet fieldSet) {
        return new Link(Integer.parseInt(fieldSet.readString(0)), fieldSet.readString(1));
    }
}
