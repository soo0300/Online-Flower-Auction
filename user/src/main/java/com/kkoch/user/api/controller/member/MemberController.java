package com.kkoch.user.api.controller.member;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.member.request.*;
import com.kkoch.user.api.controller.member.response.MemberResponse;
import com.kkoch.user.api.controller.member.response.TokenResponse;
import com.kkoch.user.api.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/user")
@Slf4j
@Api(tags = {"회원 기능"})
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @ApiOperation(value = "회원 가입")
    @PostMapping("/join")
    public ApiResponse<Long> joinMember(@Valid @RequestPart("dto") JoinMemberRequest request
            , @RequestPart("file") MultipartFile file) {
        Long memberId = memberService.join(request.toJoinMemberDto(),file);
        log.debug("memberId = {}", memberId);
        return ApiResponse.ok(memberId);

    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResponse<TokenResponse> loginMember(@Valid @RequestBody LoginMemberRequest request) {

        TokenResponse tokenResponse = memberService.login(request.toLoginMemberDto());
        return ApiResponse.ok(tokenResponse);

    }

    //회원조회(관계자) SSR
    @GetMapping("/members")
    public void getMembers() {

    }

    //내정보조회
    @ApiOperation(value = "마이페이지 정보 조회")
    @GetMapping("/my")
    public ApiResponse<MemberResponse> getMyInfo() {
        return ApiResponse.ok(null);
    }

    //내정보수정 - 비밀번호 변경
    @ApiOperation(value = "회원 비밀번호 변경")
    @PatchMapping("/my/login-pw")
    public ApiResponse<?> editMyLoginPw(@RequestBody EditLoginPwRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }

    //내정보수정 - 연락처 변경
    @ApiOperation(value = "회원 연락처 변경")
    @PatchMapping("/my/tel")
    public ApiResponse<?> editMyTel(@RequestBody EditTelRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }

    //회원탈퇴
    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/my")
    public ApiResponse<?> withdrawal(@RequestBody WithdrawalRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, "회원 탈퇴", null);
    }
}
