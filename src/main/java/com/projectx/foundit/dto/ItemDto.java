package com.projectx.foundit.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class ItemDto {
    private String item_id;
    private String item_name;
    private String description;
    private Integer category_id;
    private String location_found;
    private Time date_time_found;
    private String reported_by;
    private String contact_info;
    private List<Integer> image_ids;
    private String status;
    private Time created_at;
    private Time updated_at;

}
