package com.tisserand.service.impl;

import com.tisserand.dao.DateDao;
import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateServiceImplTest {

    @Mock
    private DateDao dateDao;


    @InjectMocks
    private DateServiceImpl dateServiceImpl;


    @Test
    void getDate() {
        String str = "2000-01-01";
        Mockito.when(dateDao.getDate()).thenReturn(str);
        String result = dateServiceImpl.getDate();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(str, result);

        Mockito.verify(dateDao).getDate();
        Mockito.verifyNoMoreInteractions(dateDao);
    }


}
