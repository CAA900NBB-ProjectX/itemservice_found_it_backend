package com.projectx.foundit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table(name = "lostitems")
@Getter
@Setter
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
    private Time dateTimeFound;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;


    public Item(String itemName, String description, Integer categoryId, String locationFound, Time dateTimeFound, String reportedBy, String status, Time createdAt) {
        this.itemName = itemName;
        this.description = description;
        this.categoryId = categoryId;
        this.locationFound = locationFound;
        this.dateTimeFound = dateTimeFound;
        this.reportedBy = reportedBy;
        this.status = status;
        this.createdAt = createdAt;
    }
}
