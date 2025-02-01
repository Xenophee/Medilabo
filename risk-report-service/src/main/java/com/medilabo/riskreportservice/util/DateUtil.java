package com.medilabo.riskreportservice.util;
import java.time.LocalDate;
import java.time.Period;


public class DateUtil {

    public static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
