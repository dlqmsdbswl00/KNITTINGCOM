package com.mini.Knit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Data // getter, setter, toString 등을 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 생성
@Alias(value = "scheduleDTO") // 별칭을 scheduleDTO로 설정

public class ScheduleDTO {
    private int scheduleId;
    private int userId; // Foreign Key
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date createdAt;

    // Getters and Setters
}
