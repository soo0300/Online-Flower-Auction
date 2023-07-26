package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.kkoch.admin.domain.admin.repository.AdminQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminQueryService {
    private final AdminQueryRepository adminQueryRepository;
    public List<AdminResponse> getAdminList(){
        return adminQueryRepository.findAllAdmin();
    }
}
