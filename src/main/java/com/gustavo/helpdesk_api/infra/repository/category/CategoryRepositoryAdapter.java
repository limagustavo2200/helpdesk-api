package com.gustavo.helpdesk_api.infra.repository.category;

import com.gustavo.helpdesk_api.core.entity.Category;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class CategoryRepositoryAdapter implements CategoryRepositoryGateway {

   private final CategoryJpaRepository jpaRepository;

   public CategoryRepositoryAdapter(CategoryJpaRepository jpaRepository) {
      this.jpaRepository = jpaRepository;
   }

   @Override
   public Category save(Category category) {
      return jpaRepository.save(category);
   }

   @Override
   public List<Category> findAll() {
      return jpaRepository.findAll();
   }

   @Override
   public Optional<Category> findById(UUID id) {
      return jpaRepository.findById(id);
   }

   @Override
   public Optional<Category> findByName(String name) {
      return jpaRepository.findByName(name);
   }

   @Override
   public void delete(Category category) {
      jpaRepository.delete(category);
   }
}
