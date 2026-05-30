package com.tenant.repository;

import com.tenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

	List<Tenant> findByOwnerId(Long ownerId);

	List<Tenant> findByOwnerIdAndActiveTrue(Long ownerId);

	List<Tenant> findByPgIdAndActiveTrue(Long pgId);
}