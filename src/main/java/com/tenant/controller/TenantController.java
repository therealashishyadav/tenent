package com.tenant.controller;

import com.tenant.dto.TenantRequest;
import com.tenant.dto.TogglePaymentRequest;
import com.tenant.model.Tenant;
import com.tenant.model.RentRecord;
import com.tenant.service.TenantService;
import com.tenant.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

	private final TenantService tenantService;
	private final JwtUtil jwtUtil;

	public TenantController(TenantService tenantService, JwtUtil jwtUtil) {
		this.tenantService = tenantService;
		this.jwtUtil = jwtUtil;
	}

	private Long getOwnerIdFromToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Missing or invalid token");
		}
		String token = authHeader.substring(7);
		Long ownerId = jwtUtil.extractOwnerId(token);
		if (ownerId == null) {
			throw new RuntimeException("Owner ID not found in token");
		}
		return ownerId;
	}

	@PostMapping
	public ResponseEntity<Tenant> addTenant(@RequestBody TenantRequest request, HttpServletRequest httpRequest) {
		Long ownerId = getOwnerIdFromToken(httpRequest);
		Tenant tenant = new Tenant();
		tenant.setOwnerId(ownerId);
		tenant.setPgId(request.getPgId());
		tenant.setFullName(request.getFullName());
		tenant.setPhone(request.getPhone());
		tenant.setTenantIdProof(request.getTenantIdProof());
		tenant.setRoomNumber(request.getRoomNumber());
		tenant.setMonthlyRent(request.getMonthlyRent());
		tenant.setMoveInDate(request.getMoveInDate());
		tenant.setActive(true);
		tenant.setNotes(request.getNotes());
		Tenant saved = tenantService.saveTenant(tenant);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping
	public ResponseEntity<List<Tenant>> getActiveTenants(HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		return ResponseEntity.ok(tenantService.getActiveTenantsForOwner(ownerId));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Tenant>> getAllTenants(HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		return ResponseEntity.ok(tenantService.getAllTenantsForOwner(ownerId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody TenantRequest request,
			HttpServletRequest httpRequest) {
		Long ownerId = getOwnerIdFromToken(httpRequest);
		Tenant existing = tenantService.getTenantById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
		if (!existing.getOwnerId().equals(ownerId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		existing.setFullName(request.getFullName());
		existing.setPhone(request.getPhone());
		existing.setTenantIdProof(request.getTenantIdProof());
		existing.setRoomNumber(request.getRoomNumber());
		existing.setMonthlyRent(request.getMonthlyRent());
		existing.setMoveInDate(request.getMoveInDate());
		existing.setNotes(request.getNotes());
		existing.setActive(request.isActive());
		return ResponseEntity.ok(tenantService.saveTenant(existing));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deactivateTenant(@PathVariable Long id, HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		Tenant tenant = tenantService.getTenantById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
		if (!tenant.getOwnerId().equals(ownerId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		tenantService.deactivateTenant(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/rent-sheet")
	public ResponseEntity<List<RentRecord>> getRentSheet(@RequestParam int year, @RequestParam int month,
			HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		return ResponseEntity.ok(tenantService.getRentSheetForOwner(ownerId, year, month));
	}

	@PostMapping("/rent-sheet/{recordId}/toggle")
	public ResponseEntity<RentRecord> togglePayment(@PathVariable Long recordId,
			@RequestBody TogglePaymentRequest request, HttpServletRequest httpRequest) {
		Long ownerId = getOwnerIdFromToken(httpRequest);
		RentRecord updated = tenantService.togglePayment(recordId, request.getNote(), ownerId);
		return ResponseEntity.ok(updated);
	}

	@GetMapping("/past-months")
	public ResponseEntity<List<Object[]>> getPastMonths(HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		return ResponseEntity.ok(tenantService.getDistinctMonthsForOwner(ownerId));
	}

	@GetMapping("/{tenantId}/history")
	public ResponseEntity<List<RentRecord>> getTenantHistory(@PathVariable Long tenantId, HttpServletRequest request) {
		Long ownerId = getOwnerIdFromToken(request);
		return ResponseEntity.ok(tenantService.getTenantHistory(tenantId, ownerId));
	}
	@DeleteMapping("/{id}/permanent")
	public ResponseEntity<Void> hardDeleteTenant(@PathVariable Long id, HttpServletRequest request) {
	    Long ownerId = getOwnerIdFromToken(request);
	    Tenant tenant = tenantService.getTenantById(id)
	        .orElseThrow(() -> new RuntimeException("Tenant not found"));
	    if (!tenant.getOwnerId().equals(ownerId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    tenantService.hardDeleteTenant(id);
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/by-pg/{pgId}")
	public ResponseEntity<List<Tenant>> getTenantsByPg(
	        @PathVariable Long pgId,
	        HttpServletRequest request) {
	    Long ownerId = getOwnerIdFromToken(request);
	    List<Tenant> tenants = tenantService.getTenantsByPg(ownerId, pgId);
	    return ResponseEntity.ok(tenants);
	}
	
}