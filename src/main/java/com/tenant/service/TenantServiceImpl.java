package com.tenant.service;

import com.tenant.model.Tenant;
import com.tenant.model.RentRecord;
import com.tenant.repository.TenantRepository;
import com.tenant.repository.RentRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

	private final TenantRepository tenantRepository;
	private final RentRecordRepository rentRecordRepository;

	public TenantServiceImpl(TenantRepository tenantRepository, RentRecordRepository rentRecordRepository) {
		this.tenantRepository = tenantRepository;
		this.rentRecordRepository = rentRecordRepository;
	}

	@Override
	public List<Tenant> getAllTenantsForOwner(Long ownerId) {
		return tenantRepository.findByOwnerId(ownerId);
	}

	@Override
	public List<Tenant> getActiveTenantsForOwner(Long ownerId) {
		return tenantRepository.findByOwnerIdAndActiveTrue(ownerId);
	}

	@Override
	public Optional<Tenant> getTenantById(Long id) {
		return tenantRepository.findById(id);
	}

	@Override
	public Tenant saveTenant(Tenant tenant) {
		return tenantRepository.save(tenant);
	}

	@Override
	@Transactional
	public void deactivateTenant(Long id) {
		tenantRepository.findById(id).ifPresent(tenant -> {
			tenant.setActive(false);
			tenantRepository.save(tenant);
		});
	}

	@Override
	public List<RentRecord> getRentSheetForOwner(Long ownerId, int year, int month) {
		List<Tenant> tenants = tenantRepository.findByOwnerIdAndActiveTrue(ownerId);
		for (Tenant t : tenants) {
			Optional<RentRecord> existing = rentRecordRepository.findByTenantIdAndYearAndMonth(t.getId(), year, month);
			if (existing.isEmpty()) {
				RentRecord newRecord = new RentRecord();
				newRecord.setTenant(t);
				newRecord.setYear(year);
				newRecord.setMonth(month);
				newRecord.setAmountDue(t.getMonthlyRent());
				newRecord.setPaid(false);
				rentRecordRepository.save(newRecord);
			}
		}
		return rentRecordRepository.findByOwnerIdAndYearAndMonth(ownerId, year, month);
	}

	@Override
	@Transactional
	public RentRecord togglePayment(Long recordId, String note, Long ownerId) {
		RentRecord record = rentRecordRepository.findById(recordId)
				.orElseThrow(() -> new RuntimeException("Rent record not found"));
		if (!record.getTenant().getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized");
		}
		record.setPaid(!record.isPaid());
		if (record.isPaid()) {
			record.setPaidOn(LocalDate.now());
		} else {
			record.setPaidOn(null);
		}
		if (note != null && !note.isBlank()) {
			record.setNote(note);
		}
		return rentRecordRepository.save(record);
	}

	@Override
	public List<Object[]> getDistinctMonthsForOwner(Long ownerId) {
		return rentRecordRepository.findDistinctMonthsByOwnerId(ownerId);
	}

	@Override
	public List<RentRecord> getTenantHistory(Long tenantId, Long ownerId) {
		Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found"));
		if (!tenant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized");
		}
		return rentRecordRepository.findByTenantIdOrderByYearDescMonthDesc(tenantId);
	}

	@Override
	public void hardDeleteTenant(Long id) {
		tenantRepository.deleteById(id);
	}
}