package com.tenant.dto;

import java.time.LocalDate;

public class TenantRequest {
	private Long pgId;
	private String fullName;
	private String phone;
	private String tenantIdProof;
	private String roomNumber;
	private Double monthlyRent;
	private LocalDate moveInDate;
	private boolean active = true;
	private String notes;

	// Getters and setters
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