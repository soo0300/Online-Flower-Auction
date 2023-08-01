package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.controller.admin.request.EditAdminRequest;
import com.kkoch.admin.api.controller.admin.request.LoginRequest;
import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import com.kkoch.admin.api.service.admin.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequestMapping("/admin-service/admin")
@RequiredArgsConstructor
@RestController
public class AdminApiController {

    private final AdminService adminService;

    //관계자 전체 조회
    @GetMapping
    public ApiResponse<List<AdminResponse>> getAdminList() {
        List<AdminResponse> adminList = adminService.getAdminList();
        return ApiResponse.ok(adminList);
    }

    //관계자 등록
    @PostMapping
    public ApiResponse<Long> addAdmin(@RequestBody AddAdminRequest request) {
        AddAdminDto dto = request.toAddAdminDto();
        Long adminId = adminService.addAdmin(dto);
        return ApiResponse.ok(adminId);

    }

    //관게자 수정
    @PatchMapping("/{adminId}")
    public ApiResponse<Long> setAdmin(@PathVariable Long adminId, @RequestBody EditAdminRequest request) {
        EditAdminDto dto = request.toEditAdminDto();
        Long setId = adminService.setAdmin(adminId, dto);
        return ApiResponse.ok(setId);

    }

    //관계자 삭제
    @DeleteMapping("/{adminId}")
    public ApiResponse<Long> removeAdmin(@PathVariable Long adminId) {
        Long deleteId = adminService.removeAdmin(adminId);
        return ApiResponse.of(MOVED_PERMANENTLY, "관계자 정보가 삭제되었습니다.", deleteId);
    }

    //관계자 로그인
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request, HttpSession session) {
        LoginDto dto = request.toLoginDto();
        LoginAdmin loginAdmin = adminService.loginAdmin(dto);
        session.setAttribute("loginAdmin", loginAdmin);
        return ApiResponse.ok("Login success!");
    }

}
