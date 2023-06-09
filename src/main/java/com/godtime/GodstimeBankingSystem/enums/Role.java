package com.godtime.GodstimeBankingSystem.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.godtime.GodstimeBankingSystem.enums.ApplicationUserPermission.*;

public enum Role {

    ADMIN(
            Sets.newHashSet(
                    EMPLOYEE_READ, EMPLOYEE_WRITE, BRANCH_READ, BRANCH_WRITE
            )
    ),
    TELLER(Sets.newHashSet()),
    LOAN_OFFICER(Sets.newHashSet()),
    MANAGER(Sets.newHashSet()),
    REVISOR(Sets.newHashSet());

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    private final Set<ApplicationUserPermission> permissions;

    Role(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream().map(permission ->
                        new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
