package com.gustavo.helpdesk_api.core.entity;

import com.gustavo.helpdesk_api.core.valueobject.Priority;
import com.gustavo.helpdesk_api.core.valueobject.Requester;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Getter
public class Ticket {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @JdbcTypeCode(Types.VARCHAR)
   private UUID id;

   @Generated(GenerationTime.ALWAYS)
   @Column(name = "ticket_number", unique = true, updatable = false, insertable = false)
   private Integer ticketNumber;

   @Embedded
   private Requester requester;

   @Column(nullable = false)
   private String title;

   @Column(columnDefinition = "text")
   private String description;

   @ManyToOne
   @JoinColumn(name = "status_id", nullable = false)
   private Status status;

   @ManyToOne
   @JoinColumn(name = "category_id", nullable = false)
   private Category category;

   @ManyToOne
   @JoinColumn(name = "subcategory_id", nullable = false)
   private Subcategory subcategory;

   @ManyToOne
   @JoinColumn(name = "system_id", nullable = false)
   private SystemEntity systemEntity;

   @Embedded
   @AttributeOverride(
      name = "label",
      column = @Column(name = "priority", nullable = false)
   )
   private Priority priority;

   @ManyToOne
   @JoinColumn(name = "assignee_id")
   private Assignee assignee;

   @ManyToOne
   @JoinColumn(name = "ti_sector_id")
   private TiSector tiSector;

   private String resolutionNote;

   @Column(nullable = false)
   private Date createdAt;

   private Date startDate;
   private Date finishedAt;


   protected Ticket() {
      // JPA
   }

   public Ticket(
      Requester requester,
      String title,
      String description,
      Priority priority,
      Category category,
      Subcategory subcategory,
      SystemEntity systemEntity,
      Status status
   ) {
      require(requester, "requester");
      require(title, "title");
      require(priority, "priority");
      require(category, "category");
      require(subcategory, "subcategory");
      require(systemEntity, "system");
      require(status, "status");

      this.createdAt = new Date();
      this.status = status;
      this.requester = requester;
      this.title = title;
      this.description = description;
      this.priority = priority;
      this.category = category;
      this.subcategory = subcategory;
      this.systemEntity = systemEntity;
   }

   private void require(Object value, String field) {

      if (value == null) {
         throw new IllegalArgumentException("Campo obrigatório não informado: " + field);
      }
      if (value instanceof String s && s.isBlank()) {
         throw new IllegalArgumentException("Campo obrigatório não pode estar vazio: " + field);
      }
   }

   private void changePriority(Priority newPriority) {
      this.priority = newPriority;
   }

   private void changeCategory(Category newCategory) {
      this.category = newCategory;
   }

   private void changeSubcategory(Subcategory newSubcategory) {
      this.subcategory = newSubcategory;
   }

   private void changeSystem(SystemEntity newSystem) {
      this.systemEntity = newSystem;
   }

   private void changeAssignee(Assignee newAssignee) {
      this.assignee = newAssignee;
   }

   private void changeTiSector(TiSector newTiSector) {
      this.tiSector = newTiSector;
   }

   private void changeResolutionNote(String newResolutionNote) {
      this.resolutionNote = newResolutionNote;
   }

   private void setStatus(Status newStatus) {

      if (newStatus == null || newStatus.equals(this.status)) {
         return;
      }

      if (this.finishedAt != null) {
         throw new IllegalStateException("Não é possível alterar status de um ticket finalizado");
      }

      if (newStatus.isFinished()) {

         if (this.tiSector == null) {
            throw new IllegalStateException("O campo Setor de T.I é obrigatório para finalizar");
         }

         if (this.assignee == null) {
            throw new IllegalStateException("O campo Responsável é obrigatório para finalizar");
         }

         if (this.resolutionNote == null) {
            throw new IllegalStateException("A nota de resolução é obrigatória para finalizar");
         }

         if (this.startDate == null) {
            this.startDate = new Date();
         }

         this.finishedAt = new Date();
      }

      if (newStatus.isInProgress() && this.startDate == null) {
         this.startDate = new Date();
      }

      this.status = newStatus;
   }

   public boolean hasStatusChanged(Status newStatus) {
      if (this.status == null || newStatus == null) return true;
      return !this.status.getId().equals(newStatus.getId());
   }

   public void updateTicket(
      Status newStatus,
      Priority newPriority,
      Category newCategory,
      Subcategory newSubcategory,
      SystemEntity newSystem,
      Assignee newAssignee,
      TiSector newTiSector,
      String newResolutionNote
   ) {

      if (this.finishedAt != null) {
         throw new IllegalStateException("Tickets finalizados não podem ser alterados");
      }

      if (newPriority != null && !newPriority.equals(this.priority)) {
         changePriority(newPriority);
      }

      if (newCategory != null && !newCategory.equals(this.category)) {
         changeCategory(newCategory);
      }

      if (newSubcategory != null && !newSubcategory.equals(this.subcategory)) {
         changeSubcategory(newSubcategory);
      }

      if (newSystem != null && !newSystem.equals(this.systemEntity)) {
         changeSystem(newSystem);
      }

      if (newAssignee != null && !newAssignee.equals(this.assignee)) {
         changeAssignee(newAssignee);
      }

      if (newTiSector != null && !newTiSector.equals(this.tiSector)) {
         changeTiSector(newTiSector);
      }

      if (newResolutionNote != null) {

         if (newStatus == null || !newStatus.isFinished()) {
            throw new IllegalStateException(
               "Nota de resolução só pode ser preenchida quando o ticket estiver sendo finalizado"
            );
         }

         changeResolutionNote(newResolutionNote);
      }

      if (newStatus != null && !newStatus.equals(this.status)) {
         setStatus(newStatus);
      }
   }
}

