package org.example.springsecurity.auth.controller.Test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/superAdmin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('super_admin:read')")
    public String getSuperAdminInfo() {
        return "Super Admin Information";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('super_admin:update')")

    public String updateSuperAdmin() {
        return "Super Admin Updated";
    }
}
