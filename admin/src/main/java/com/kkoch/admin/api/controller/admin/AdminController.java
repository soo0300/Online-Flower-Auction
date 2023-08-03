package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.controller.admin.request.LoginRequest;
import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.kkoch.admin.api.service.admin.AdminQueryService;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.LoginDto;
import com.kkoch.admin.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final AdminQueryService adminQueryService;

    @GetMapping
    public String index(@ModelAttribute(name = "form") LoginRequest request, @Login LoginAdmin loginAdmin) {
        if (loginAdmin == null) {
            return "index";
        }
        return "dashboard";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(name = "form") LoginRequest request, HttpSession session) {
        LoginDto dto = request.toLoginDto();
        LoginAdmin loginAdmin = adminQueryService.loginAdmin(dto);
        session.setAttribute("loginAdmin", loginAdmin);
        return "redirect:/";
    }

    @GetMapping("/admins")
    public String adminPage(@ModelAttribute("form") AddAdminRequest request, Model model) {
        List<AdminResponse> responses = adminQueryService.getAdmins();
        model.addAttribute("admins", responses);
        return "admin";
    }

    @PostMapping("/admins/add")
    public String addAdmin(AddAdminRequest request) {
        AddAdminDto dto = request.toAddAdminDto();
        Long adminId = adminService.addAdmin(dto);
        log.info("admin={}", adminId);
        return "redirect:/admins";
    }
}
