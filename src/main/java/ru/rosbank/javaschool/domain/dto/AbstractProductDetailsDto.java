package ru.rosbank.javaschool.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;

@Data
@AllArgsConstructor
public abstract class AbstractProductDetailsDto {
    private int id;
    private String name;
    private int price;
    private int kkal;
    private String description;

    public abstract AbstractProductModel toModel();
}
