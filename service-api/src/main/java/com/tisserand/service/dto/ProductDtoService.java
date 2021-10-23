package com.tisserand.service.dto;

import com.tisserand.model.dto.ProductDto;

import java.util.List;

public interface ProductDtoService {
    List<ProductDto> findAllProductWithNameOwner();

    List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate);
}
