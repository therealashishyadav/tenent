package com.tenant.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tenants")
public class Tenant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "owner_id", nullable = false)
	private Long ownerId;

	@Column(name = "pg_id")
	private Long pgId;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "tenant_id_proof")
	private String tenantIdProof;

	@Column(name = "room_number", nullable = false)
	private String roomNumber;

	@Column(name = "monthly_rent", nullable = false)
	private Double monthlyRent;

	@Column(name = "move_in_date")
	private LocalDate moveInDate;

	@Column(name = "active", nullable = false)
	private boolean active = true;

	@Column(name = "notes")
	private String notes;

	public Tenant() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getPgId() {
		return pgId;
	}

	public void setPgId(Long pgId) {
		this.pgId = pgId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTenantIdProof() {
		return tenantIdProof;
	}

	public void setTenantIdProof(String tenantIdProof) {
		this.tenantIdProof = tenantIdProof;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Double getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(Double monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public LocalDate getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDate moveInDate) {
		this.moveInDate = moveInDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}