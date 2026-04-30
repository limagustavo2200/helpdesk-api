package com.gustavo.helpdesk_api.infra.repository.subcategory;

import com.gustavo.helpdesk_api.core.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubcategoryJpaRepository extends JpaRepository<Subcategory, UUID> {
    Optional<Subcategory> findByName(String name);
}