package com.projectx.foundit.repository;

import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {



}
