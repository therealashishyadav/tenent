package com.tenant.repository;

import com.tenant.model.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentRecordRepository extends JpaRepository<RentRecord, Long> {

	@Query("SELECT r FROM RentRecord r WHERE r.tenant.ownerId = :ownerId AND r.year = :year AND r.month = :month")
	List<RentRecord> findByOwnerIdAndYearAndMonth(Long ownerId, int year, int month);

	List<RentRecord> findByTenantIdOrderByYearDescMonthDesc(Long tenantId);

	Optional<RentRecord> findByTenantIdAndYearAndMonth(Long tenantId, int year, int month);

	@Query("SELECT DISTINCT r.year, r.month FROM RentRecord r WHERE r.tenant.ownerId = :ownerId ORDER BY r.year DESC, r.month DESC")
	List<Object[]> findDistinctMonthsByOwnerId(Long ownerId);
}