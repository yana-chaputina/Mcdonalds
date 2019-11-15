package ru.rosbank.javaschool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;

@Data
@AllArgsConstructor
public abstract class AbstractProductModel {
    private int id;
    private String name;
    private int price;
    private int kkal;
    private String description;

    public abstract AbstractProductDetailsDto toDto();

}
