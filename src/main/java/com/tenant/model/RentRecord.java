package com.tenant.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rent_records", uniqueConstraints = { @UniqueConstraint(columnNames = { "tenant_id", "year", "month" }) })
public class RentRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tenant_id", nullable = false)
	private Tenant tenant;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private int month;

	@Column(name = "amount_due", nullable = false)
	private Double amountDue;

	@Column(nullable = false)
	private boolean paid = false;

	@Column(name = "paid_on")
	private LocalDate paidOn;

	@Column(name = "note")
	private String note;

	public RentRecord() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Double amountDue) {
		this.amountDue = amountDue;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public LocalDate getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(LocalDate paidOn) {
		this.paidOn = paidOn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}