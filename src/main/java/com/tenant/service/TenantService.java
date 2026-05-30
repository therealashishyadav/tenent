package com.tenant.service;

import com.tenant.model.Tenant;
import com.tenant.model.RentRecord;
import java.util.List;
import java.util.Optional;

public interface TenantService {
    List<Tenant> getAllTenantsForOwner(Long ownerId);
    List<Tenant> getActiveTenantsForOwner(Long ownerId);
    Optional<Tenant> getTenantById(Long id);
    Tenant saveTenant(Tenant tenant);
    void deactivateTenant(Long id);
    
    List<RentRecord> getRentSheetForOwner(Long ownerId, int year, int month);
    RentRecord togglePayment(Long recordId, String note, Long ownerId);
    List<Object[]> getDistinctMonthsForOwner(Long ownerId);
    List<RentRecord> getTenantHistory(Long tenantId, Long ownerId);
}