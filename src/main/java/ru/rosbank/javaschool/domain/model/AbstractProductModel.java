package ru.rosbank.javaschool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractProductModel {
    private int id;
    private String name;
    private int price;
    private int kkal;
    private String description;

    public abstract AbstractProductDetailsDto toDto();

}
