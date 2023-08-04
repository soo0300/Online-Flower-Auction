package com.kkoch.user.api.controller.member;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.member.request.*;
import com.kkoch.user.api.controller.member.response.MemberResponse;
import com.kkoch.user.api.controller.member.response.TokenResponse;
import com.kkoch.user.api.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
@Slf4j
public class MemberController {

    private final MemberService memberService;
//    private final FileStore fileStore;

    @PostMapping("/join")
    public ApiResponse<MemberResponse> joinMember(@Valid @RequestBody JoinMemberRequest request
//            , @RequestPart("file") MultipartFile file) throws IOException {
    ) {
//        fileStore.storeFile(file);
        MemberResponse response = memberService.join(request.toJoinMemberDto());
        log.debug("MemberResponse={}", response);
        return ApiResponse.ok(response);
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> loginMember(@Valid @RequestBody LoginRequest request) {

//        TokenResponse tokenResponse = memberService.login(request.toLoginMemberDto());
//        return ApiResponse.ok(tokenResponse);
        return null;
    }

    //회원조회(관계자) SSR
    @GetMapping("/members")
    public void getMembers() {

    }


    //내정보조회
//    @ApiOperation(value = "마이페이지 정보 조회")
//    @GetMapping("/my")
//    public ApiResponse<MemberResponse> getMyInfo() {
//        return ApiResponse.ok(null);
//    }

    //내정보수정 - 비밀번호 변경
    @PatchMapping("/my/login-pw")
    public ApiResponse<?> editMyLoginPw(@RequestBody EditLoginPwRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }

    //내정보수정 - 연락처 변경
    @PatchMapping("/my/tel")
    public ApiResponse<?> editMyTel(@RequestBody EditTelRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }

    //회원탈퇴
    @DeleteMapping("/my")
    public ApiResponse<?> withdrawal(@RequestBody WithdrawalRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, "회원 탈퇴", null);
    }

}
