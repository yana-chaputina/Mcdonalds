package ru.rosbank.javaschool.domain.dto;

import lombok.Data;
import ru.rosbank.javaschool.domain.model.DrinkModel;

@Data
public class DrinkDetailsDto extends AbstractProductDetailsDto {
    private int volume;

    public DrinkDetailsDto(int id, String name, int price, int kkal, String description, int volume) {
        super(id, name, price, kkal, description);
        this.volume = volume;
    }


    @Override
    public DrinkModel toModel() {
        return new DrinkModel(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription(),
                this.getVolume());
    }
}
