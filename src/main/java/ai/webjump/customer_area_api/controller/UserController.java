package ai.webjump.customer_area_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PreAuthorize("hasRole('readuser')")
    @GetMapping("/api/resource/ru")
    public String roleReadUser() {
        return "Resource protected by role readuser";
    }

    @PreAuthorize("hasRole('updateuser')")
    @GetMapping("/api/resource/uu")
    public String roleUpdateUser() {
        return "Resource protected by role updateuser";
    }

    //@PreAuthorize("hasAuthority('ROLE_inexistroleuser')")
    @PreAuthorize("hasRole('inexistroleuser')")
    @GetMapping("/api/resource/iru")
    public String inexistRoleUser() {
        return "If you got this far, it went wrong because this role does not exist. Check if the role validation is actually being done.";
    }
}
