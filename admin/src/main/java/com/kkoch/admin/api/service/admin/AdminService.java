package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import com.kkoch.admin.api.service.admin.dto.LoginDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminQueryRepository;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminQueryRepository adminQueryRepository;

    public Long addAdmin(AddAdminDto dto) {
        Admin admin = dto.toEntity();
        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin.getId();
    }

    public Long setAdmin(Long adminId, EditAdminDto dto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()
                        -> new NoSuchElementException("존재하지 않는 관게자"));
        admin.changeAdmin(dto.getLoginPw(), dto.getTel());
        return admin.getId();
    }

    public Long removeAdmin(Long admindId) {
        Admin admin = adminRepository.findById(admindId)
                .orElseThrow(()
                        -> new NoSuchElementException("존재하지 않는 관게자"));
        admin.removeAdmin();
        return admin.getId();
    }

    public List<AdminResponse> getAdminList() {
        return adminQueryRepository.findAllAdmin();
    }

    public LoginAdmin loginAdmin(LoginDto dto) {
        LoginAdmin admin = adminQueryRepository.findByLoginIdAndLoginPw(dto.getLoginId(), dto.getLoginPw());

        if (admin == null) {
            throw new NoSuchElementException("아이디와 비밀번호를 확인하세요");
        }

        return admin;

    }
}
