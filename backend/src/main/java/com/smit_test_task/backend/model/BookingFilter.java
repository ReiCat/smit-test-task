package com.smit_test_task.backend.model;

import java.time.ZoneId;
import java.util.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingFilter {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer page;
    private Integer amount;

    public Date convertToLocalDateViaSqlDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public BookingFilter(Date fromDate, Date toDate) {
        this.page = 0;
        this.fromDate = convertToLocalDateTimeViaInstant(fromDate);
        this.toDate = convertToLocalDateTimeViaInstant(toDate);

        Duration duration = Duration.between(this.fromDate, this.toDate);
        long hours = duration.toHours();

        this.amount = 0;
        if (hours > 0) {
            this.amount = (int) (long) hours;
        }
    }

}
