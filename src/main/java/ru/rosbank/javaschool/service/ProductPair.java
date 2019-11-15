package ru.rosbank.javaschool.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPair {
    private int firstProductId;
    private int secondProductId;
    private String key;
    private int value;
}