package ru.rosbank.javaschool.domain.dto;

import lombok.Data;
import ru.rosbank.javaschool.domain.model.PotatoModel;

@Data
public class PotatoDetailsDto extends AbstractProductDetailsDto {
    private int size;

    public PotatoDetailsDto(int id, String name, int price, int kkal, String description, int size) {
        super(id, name, price, kkal, description);
        this.size = size;
    }


    @Override
    public PotatoModel toModel() {
        return new PotatoModel(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription(),
                this.getSize()
        );
    }
}
