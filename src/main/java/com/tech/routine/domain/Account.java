package com.tech.routine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Persistable<UUID> {
    @Id
    private UUID id;
    private String documentNumber;

    @Transient
    private boolean whetherNew = false;

    @Override
    public boolean isNew() {
        return whetherNew;
    }
}
