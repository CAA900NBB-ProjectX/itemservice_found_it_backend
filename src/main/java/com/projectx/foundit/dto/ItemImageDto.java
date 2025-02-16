package com.projectx.foundit.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemImageDto {
    private long item_image_id;
    private long item_id;
    private String description;
    private byte image;
    private String image_name;
    private LocalDateTime date_time;
    private boolean status;
    private Time created_at;


}
