package com.tech.routine.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tech.routine.exception.BaseException;
import com.tech.routine.exception.ValidationException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransactionType {
    NORMAL_PURCHASE(1, "Normal Purchase", EntryDirection.NEGATIVE),
    PURCHASE_WITH_INSTALLMENTS(2, "Purchase With Installments", EntryDirection.NEGATIVE),
    WITHDRAWL(3, "Withdrawl", EntryDirection.NEGATIVE),
    CREDIT_VOUCHER(4, "Credit Voucher", EntryDirection.POSITIVE);

    TransactionType(Integer value, String description, EntryDirection direction) {
        this.value = value;
        this.description = description;
        this.direction = direction;
    }

    @JsonCreator
    public static TransactionType getInstance(Integer key) {
        // Todo: replace with key post meeting
        return Arrays.stream(TransactionType.values())
                .filter(exampleEnum -> exampleEnum.getValue().equals(key))
                .findAny()
                .orElseThrow(() -> new ValidationException("Invalid Enum value"));
    }

    @JsonValue // TODO: remove after meeting
    private final Integer value;
    private final String description;
    private final EntryDirection direction;
}
