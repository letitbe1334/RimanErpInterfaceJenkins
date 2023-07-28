package biz.riman.erp.batch.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseDto {
    // 최초등록일
    @JsonIgnore
    public String getCreatedDatetime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    // 최종변경일
    @JsonIgnore
    public String getModifiedDatetime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
