package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin-service/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    //관계자 등록
    @PostMapping
    public ApiResponse<Long> addAdmin(@RequestParam AddAdminRequest request) {
        AddAdminDto dto = request.toAddAdminDto();
        Long adminId = adminService.addAdmin(dto);
        return ApiResponse.ok(adminId);

    }

}
