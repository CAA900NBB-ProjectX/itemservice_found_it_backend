package com.projectx.foundit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;


@Entity
@Table(name = "lostitems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "location_found")
    private String locationFound;

    @Column(name = "date_time_found")
    private LocalTime dateTimeFound;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalTime  createdAt;

    @Column(name = "updated_at")
    private LocalTime updatedAt;


    public Item(int item_id, String itemName, String description, Integer categoryId, String locationFound, Time dateTimeFound, String reportedBy, String contactInfo, String status, Time createdAt, Time updatedAt) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.description = description;
        this.categoryId = categoryId;
        this.locationFound = locationFound;
        this.dateTimeFound = dateTimeFound.toLocalTime();
        this.reportedBy = reportedBy;
        this.contactInfo = contactInfo;
        this.status = status;
        this.createdAt = LocalTime.now();
        this.updatedAt = LocalTime.now();
    }
}
