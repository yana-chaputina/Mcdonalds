package ru.rosbank.javaschool.service;

import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;
import ru.rosbank.javaschool.domain.dto.ProductDto;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.order.Order;
import ru.rosbank.javaschool.domain.order.Sale;
import ru.rosbank.javaschool.exception.DataNotFoundException;
import ru.rosbank.javaschool.exception.InvalidDataException;
import ru.rosbank.javaschool.repository.OrderRepository;
import ru.rosbank.javaschool.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService implements ProductServiceInterface {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public AbstractProductModel saveProduct(AbstractProductDetailsDto dto) {

        if (dto.getId() < 0) {
            throw new InvalidDataException("Id can't be negative");
        }

        if (dto.getName() == null) {
            throw new InvalidDataException("Product must have a name");
        }
        if (dto.getPrice() < 0) {
            throw new InvalidDataException("Price can't be negative");
        }
        if (dto.getKkal() < 0) {
            throw new InvalidDataException("The energy value can't be negative");
        }

        if (dto.getId() == 0) {
            return productRepository.create(dto.toModel());
        }

        return productRepository.update(dto.toModel());
    }

    @Override
    public Order saveOrder(Order order) {

        if (order.getId() < 0) {
            throw new InvalidDataException("Id can't be negative");
        }
        if (order.getSales() == null) {
            throw new InvalidDataException("Order must have sales");
        }
        if (order.getId() == 0) {
            return orderRepository.create(order);
        }

        return orderRepository.update(order);
    }

    @Override
    public Collection<ProductDto> searchProduct(String text) {
        return productRepository.getAll().stream()
                .filter(o -> o.getName().toLowerCase().contains(text.toLowerCase()))
                .map(ProductDto::from)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public Collection<ProductDto> getProductInCategory(String category) {
        return productRepository.getAll().stream()
                .filter(o -> o.getClass().getSimpleName().toLowerCase().contains(category.toLowerCase()))
                .map(ProductDto::from)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public AbstractProductDetailsDto getProductById(int id) {
        return productRepository.getById(id)
                .map(AbstractProductModel::toDto)
                .orElseThrow(DataNotFoundException::new)
                ;
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.getById(id)
                .orElseThrow(DataNotFoundException::new)
                ;
    }

    @Override
    public Collection<ProductDto> getAllProduct() {
        return productRepository.getAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public Collection<Order> getAllOrder() {

        return orderRepository.getAll().stream()
                .collect(Collectors.toList())
                ;
    }

    @Override
    public boolean removeOrderById(int id) {
        boolean removed = orderRepository.removeById(id);
        if (!removed) {
            throw new DataNotFoundException();
        }
        return true;
    }

    @Override
    public boolean removeProductById(int id) {
        boolean removed = productRepository.removeById(id);
        if (!removed) {
            throw new DataNotFoundException();
        }
        return true;
    }

    @Override
    public Collection<ProductValue> getMostPopularProduct() {
        List<ProductValue> result = new ArrayList<ProductValue>();
        for (Order order : orderRepository.getAll()) {
            for (Sale sale : order.getSales()) {
                boolean productIsExist = false;
                for (ProductValue productValue : result) {
                    if (sale.getProductId() == productValue.getId()) {
                        int newCount = productValue.getValue() + sale.getCount();
                        productValue.setValue(newCount);
                        productIsExist = true;
                        break;
                    }
                }
                if (!productIsExist) {
                    result.add(new ProductValue(sale.getProductId(), sale.getCount()));
                }

            }
        }
        return getThreeElementsWithMaxValue(result);
    }

    private Collection<ProductValue> getThreeElementsWithMaxValue(List<ProductValue> list) {
        list.sort((o1, o2) -> -(o1.getValue() - o2.getValue()));
        return list.subList(0, 3);
    }

    @Override
    public Collection<ProductValue> getMostProfitableProduct() {
        List<ProductValue> result = new ArrayList<ProductValue>();
        for (Order order : orderRepository.getAll()) {
            for (Sale sale : order.getSales()) {
                boolean productIsExist = false;
                for (ProductValue productValue : result) {
                    if (sale.getProductId() == productValue.getId()) {
                        int newCost = productValue.getValue() + sale.getCount() * sale.getPrice();
                        productValue.setValue(newCost);
                        productIsExist = true;
                        break;
                    }
                }
                if (!productIsExist) {
                    result.add(new ProductValue(sale.getProductId(), sale.getCount() * sale.getPrice()));
                }

            }
        }
        return getThreeElementsWithMaxValue(result);
    }

    private List<ProductPair> getAllProductPair() {
        List<ProductPair> result = new ArrayList<>();
        for (Order order : orderRepository.getAll()) {
            for (int i = 0; i < order.getSales().size() - 1; i++) {
                for (int j = i + 1; j < order.getSales().size(); j++) {
                    ProductPair productPair = new ProductPair();
                    if (order.getSales().get(i).getProductId() < order.getSales().get(j).getProductId()) {
                        productPair.setFirstProductId(order.getSales().get(i).getProductId());
                        productPair.setSecondProductId(order.getSales().get(j).getProductId());
                        productPair.setKey("" + order.getSales().get(i).getProductId() + " " + order.getSales().get(j).getProductId());
                    }
                    if (order.getSales().get(i).getProductId() > order.getSales().get(j).getProductId()) {
                        productPair.setFirstProductId(order.getSales().get(j).getProductId());
                        productPair.setSecondProductId(order.getSales().get(i).getProductId());
                        productPair.setKey("" + order.getSales().get(j).getProductId() + " " + order.getSales().get(i).getProductId());
                    }
                    result.add(productPair);
                }
            }

        }
        return result;
    }

    private KeyValue getPairWithMaxValue(List<KeyValue> list) {
        list.sort((o1, o2) -> -(o1.getValue() - o2.getValue()));
        return list.get(0);
    }

    @Override
    public KeyValue getMostPopularPair() {
        List<KeyValue> result = new ArrayList<>();
        List<ProductPair> listProductPair = getAllProductPair();
        for (ProductPair productPair : listProductPair) {
            boolean keyIsExist = false;
            for (KeyValue keyValue : result) {
                if (productPair.getKey().equals(keyValue.getKey())) {
                    int newCount = keyValue.getValue();
                    newCount++;
                    keyValue.setValue(newCount);
                    keyIsExist = true;
                    break;
                }
            }
            if (!keyIsExist) {
                result.add(new KeyValue(productPair.getKey(), 1));
            }
        }
        return getPairWithMaxValue(result);
    }

    @Override
    public int recommendedWith(int id) {
        List<KeyValue> result = new ArrayList<>();
        List<ProductPair> listProductPair = getAllProductPair();
        for (ProductPair productPair : listProductPair) {
            if (productPair.getFirstProductId() != id && productPair.getSecondProductId() != id) {
                continue;
            }
            boolean keyIsExist = false;
            for (KeyValue keyValue : result) {
                if (productPair.getKey().equals(keyValue.getKey())) {
                    int newCount = keyValue.getValue() + 1;
                    keyValue.setValue(newCount);
                    keyIsExist = true;
                    break;
                }
            }
            if (!keyIsExist) {
                result.add(new KeyValue(productPair.getKey(), 1));
            }
        }
        String idKey = getPairWithMaxValue(result).getKey();
        String[] stringKey = idKey.split("\\s");
        if (Integer.parseInt(stringKey[0]) == id) {
            return Integer.parseInt(stringKey[1]);
        }
        return Integer.parseInt(stringKey[0]);
    }
}
