package com.vanguard.weather.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class UtilityServiceTest {

    private UtilityService utilityService;

    private final String pattern = "MM-dd-yyyy:hh";

    @BeforeEach
    void setUp() {
        utilityService = new UtilityService();
        ReflectionTestUtils.setField(utilityService, "pattern", pattern);
    }

    @Test
    void currentDate() {
        String currentDate = utilityService.currentDate();
        Assertions.assertNotNull(currentDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Assertions.assertEquals(currentDate, date);
    }
}