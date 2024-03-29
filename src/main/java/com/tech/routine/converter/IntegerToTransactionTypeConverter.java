package com.tech.routine.converter;

import com.tech.routine.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;

@ReadingConverter
public class IntegerToTransactionTypeConverter implements Converter<Integer, TransactionType> {

    @Override
    public TransactionType convert(@NotNull Integer source) {
        return Arrays.stream(TransactionType.values())
                .filter(transactionType -> transactionType.getValue().equals(source))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
