package com.gustavo.helpdesk_api.infra.repository.category;

import com.gustavo.helpdesk_api.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
}