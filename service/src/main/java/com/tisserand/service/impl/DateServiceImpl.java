package com.tisserand.service.impl;

import com.tisserand.dao.DateDao;
import com.tisserand.dao.PaymentDao;
import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import com.tisserand.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DateServiceImpl implements DateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateServiceImpl.class);

    private final DateDao dateDao;
    private final PaymentService paymentService;

    @Autowired
    public DateServiceImpl(DateDao dateDao, PaymentService paymentService) {
        this.dateDao = dateDao;
        this.paymentService = paymentService;
    }


    @Override
    public String getDate() {
        LOGGER.debug("DateServiceImpl: getDate()");
        return dateDao.getDate();
    }


    @Override
    public Integer update(String date) {
        LOGGER.debug("DateServiceImpl: update({})", date);
        paymentService.payment(date);
        return dateDao.update(date);
    }

}
