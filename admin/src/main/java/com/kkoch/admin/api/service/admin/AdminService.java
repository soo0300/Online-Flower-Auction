package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public Long addAdmin(AddAdminDto dto) {
        Admin admin = dto.toEntity();
        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin.getId();
    }

}
