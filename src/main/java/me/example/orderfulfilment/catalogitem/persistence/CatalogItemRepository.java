package me.example.orderfulfilment.catalogitem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogItemRepository extends JpaRepository<CatalogItemEntity, Integer> {
}
