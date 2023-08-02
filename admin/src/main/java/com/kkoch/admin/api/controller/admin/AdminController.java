package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.controller.admin.request.LoginRequest;
import com.kkoch.admin.api.service.admin.AdminQueryService;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.LoginDto;
import com.kkoch.admin.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
}
