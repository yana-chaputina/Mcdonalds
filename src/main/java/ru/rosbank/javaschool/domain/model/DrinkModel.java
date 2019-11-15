package ru.rosbank.javaschool.domain.model;

import lombok.Data;
import ru.rosbank.javaschool.domain.dto.DrinkDetailsDto;

@Data
public class DrinkModel extends AbstractProductModel {
    private int volume;

    public DrinkModel(int id, String name, int price, int kkal, String description, int volume) {
        super(id, name, price, kkal, description);
        this.volume = volume;
    }


    @Override
    public DrinkDetailsDto toDto() {
        return new DrinkDetailsDto(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription(),
                this.getVolume());
    }
}
