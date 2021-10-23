package com.tisserand.dao.dto;

import com.tisserand.model.dto.ProductDto;

import java.util.List;

public interface ProductDtoDao {
    List<ProductDto> findAllProductWithNameOwner();

    List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate);
}
