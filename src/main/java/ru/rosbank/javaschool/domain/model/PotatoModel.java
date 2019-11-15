package ru.rosbank.javaschool.domain.model;

import lombok.Data;
import ru.rosbank.javaschool.domain.dto.PotatoDetailsDto;

@Data
public class PotatoModel extends AbstractProductModel {
    private int size;

    public PotatoModel(int id, String name, int price, int kkal, String description, int size) {
        super(id, name, price, kkal, description);
        this.size = size;
    }

    @Override
    public PotatoDetailsDto toDto() {
        return new PotatoDetailsDto(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription(),
                this.getSize()
        );
    }
}