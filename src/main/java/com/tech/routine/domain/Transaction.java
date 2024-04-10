package com.tech.routine.domain;

import com.tech.routine.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID accountId;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balance;
    @CreatedDate
    private Instant createdAt;
    @Version
    private Integer version;

    @Transient
    private boolean whetherNew = false;

    @Override
    public boolean isNew() {
        return whetherNew;
    }
}
