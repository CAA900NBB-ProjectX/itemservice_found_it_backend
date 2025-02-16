package com.projectx.foundit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


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
    private LocalDateTime dateTimeFound;

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

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<ItemImage> images;

    @Column(name = "image_ids")
    private List<Long> imageIdsList;


    public Item(int item_id, String itemName, String description, Integer categoryId, String locationFound,
                LocalDateTime dateTimeFound, String reportedBy, String contactInfo, String status, Time createdAt,
                Time updatedAt, List<Long> imageIdsList) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.description = description;
        this.categoryId = categoryId;
        this.locationFound = locationFound;
        this.dateTimeFound = dateTimeFound;
        this.reportedBy = reportedBy;
        this.contactInfo = contactInfo;
        this.status = status;
        this.createdAt = LocalTime.now();
        this.updatedAt = LocalTime.now();
        this.imageIdsList = imageIdsList;

    }

}
