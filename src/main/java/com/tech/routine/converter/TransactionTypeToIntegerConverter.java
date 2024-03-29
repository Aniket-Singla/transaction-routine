package com.tech.routine.converter;

import com.tech.routine.enums.TransactionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class TransactionTypeToIntegerConverter implements Converter<TransactionType, Integer> {
    @Override
    public Integer convert(TransactionType source) {
        return source.getValue();
    }
}
