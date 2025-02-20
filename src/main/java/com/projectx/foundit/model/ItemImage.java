package com.projectx.foundit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.projectx.foundit.core.Base64ImageDeserializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Entity
@Table(name = "itemimages")
@Getter
@Setter
@NoArgsConstructor
public class ItemImage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item itemID;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    @JsonDeserialize(using = Base64ImageDeserializer.class)
    private byte[] image;

    @Column(name = "location_found")
    private String locationFound;

    @Column(name = "date_time")
    private LocalTime dateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalTime createdAt;

    @Column(name = "updated_at")
    private LocalTime updatedAt;

    public ItemImage(long id, Item itemID, String description, byte[] image, String locationFound, LocalTime dateTime, String status, LocalTime createdAt, LocalTime updatedAt) {
        this.id = id;
        this.itemID = itemID;
        this.description = description;
        this.image = image;
        this.locationFound = locationFound;
        this.dateTime = dateTime;
        this.status = status;
        this.updatedAt = updatedAt;
    }

}
