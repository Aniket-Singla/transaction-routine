package com.tech.routine.config;

import com.tech.routine.converter.IntegerToTransactionTypeConverter;
import com.tech.routine.converter.TransactionTypeToIntegerConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
public class CustomConvertors {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
        var dialect = DialectResolver.getDialect(connectionFactory);
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new IntegerToTransactionTypeConverter());
        converterList.add(new TransactionTypeToIntegerConverter());
        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder(), converterList),
                Collections.emptyList()
        );
    }
}