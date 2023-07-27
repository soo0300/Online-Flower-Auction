package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.admin.response.AdminResponse;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminQueryRepository;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
class AdminServiceTest extends IntegrationTestSupport {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminQueryRepository adminQueryRepository;

    @DisplayName("관계자 더미데이터로 서비스를 통해 관계자 등록 후 리포지토리로 확인하기.")
    @Test
    public void addAdminTest() throws Exception {
        // given
        AddAdminDto addAdminDto = AddAdminDto.builder()
                .loginId("soo0300")
                .loginPw("0300soo0989")
                .tel("010-1234-5678")
                .name("kim")
                .position("경매")
                .build();

        // when
        Long adminId = adminService.addAdmin(addAdminDto);

        // then
        Optional<Admin> admin = adminRepository.findById(adminId);
        Assertions.assertThat(admin).isPresent();
    }

    @DisplayName("등록된 관계자의 목록을 조회할 수 있다.")
    @Test
    public void getAdmin() throws Exception {
        // given
        Admin admin = insertAdmin();
        Admin admin2 = Admin.builder()
                .loginId("molly")
                .loginPw("konglish")
                .name("jin")
                .position("30")
                .tel("010-1234-1234")
                .active(true)
                .build();
        adminRepository.save(admin2);

        // when
        List<AdminResponse> admins = adminService.getAdminList();

        // then
        Assertions.assertThat(admins.size()).isEqualTo(2);
        Assertions.assertThat(admins.get(1).getName()).isEqualTo("jin");
    }

    @DisplayName("관계자 정보에서 비밀번호와 전화번호만 변경할 수 있다.")
    @Test
    public void setAdminTest() throws Exception {
        //given
        Admin admin = insertAdmin();

        EditAdminDto dto = EditAdminDto.builder()
                .loginPw("990505soo0300")
                .tel("010-0234-1203")
                .build();

        // when
        Long adminId = adminService.setAdmin(admin.getId(), dto);

        // then
        Optional<Admin> changedAdmin = adminRepository.findById(adminId);
        Assertions.assertThat(changedAdmin.get().getTel()).isEqualTo("010-0234-1203");

    }

    @DisplayName("등록된 관계자를 삭제하면 비활성화 한다.")
    @Test
    public void removeAdmin() throws Exception {
        // given
        Admin admin = insertAdmin();
        Long adminId = admin.getId();

        // when
        Long deleteId = adminService.removeAdmin(adminId);

        // then
        Optional<Admin> deleteAdmin = adminRepository.findById(deleteId);
        Assertions.assertThat(deleteAdmin.get().isActive()).isEqualTo(false);

    }

    @DisplayName("관계자는 아이디와 비밀번호를 통해서 관리자 페이지에 로그인한다.")
    @Test
    public void loginAdmin() throws Exception {
        // given
        Admin admin = insertAdmin();

        // when
        boolean loginSuccess = adminService.loginAdmin(admin.getLoginId(), admin.getLoginPw());

        // then
        Assertions.assertThat(loginSuccess).isEqualTo(true);
    }


    private Admin insertAdmin() {
        Admin admin = Admin.builder()
                .loginId("admin")
                .loginPw("admin123!")
                .name("관리자")
                .position("10")
                .tel("010-0000-0000")
                .active(true)
                .build();
        return adminRepository.save(admin);
    }
}
