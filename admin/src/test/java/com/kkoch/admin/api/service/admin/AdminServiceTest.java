package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
class AdminServiceTest extends IntegrationTestSupport {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("관계자 더미데이터로 서비스를 통해 관계자 등록 후 리포지토리로 확인하기.")
    @Test
    public void addAdminTest() throws Exception {
        // given
        AddAdminDto addAdminDto = AddAdminDto.builder()
                .loginId("soo")
                .loginPw("0300")
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

}
