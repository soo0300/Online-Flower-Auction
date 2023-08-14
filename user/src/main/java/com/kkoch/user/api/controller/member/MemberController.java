package com.kkoch.user.api.controller.member;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.member.request.CheckEmailRequest;
import com.kkoch.user.api.controller.member.request.JoinMemberRequest;
import com.kkoch.user.api.controller.member.request.SetPasswordRequest;
import com.kkoch.user.api.controller.member.request.WithdrawalRequest;
import com.kkoch.user.api.controller.member.response.MemberInfoResponse;
import com.kkoch.user.api.controller.member.response.MemberResponse;
import com.kkoch.user.api.controller.member.response.MemberResponseForAdmin;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
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

    @GetMapping("/members")
    public List<MemberResponseForAdmin> getUsers() {
        return memberQueryService.getUsers();
    }

    @GetMapping("/{memberKey}")
    public ApiResponse<MemberInfoResponse> getMyInfo(@PathVariable String memberKey) {
        MemberInfoResponse response = memberQueryService.getMyInfo(memberKey);
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{memberKey}/pwd")
    public ApiResponse<MemberResponse> setPassword(@PathVariable String memberKey, @Valid @RequestBody SetPasswordRequest request) {
        MemberResponse response = memberService.setPassword(memberKey, request.getCurrentPwd(), request.getNewPwd());
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{memberKey}")
    public ApiResponse<MemberResponse> withdrawal(@PathVariable String memberKey, @Valid @RequestBody WithdrawalRequest request) {
        MemberResponse response = memberService.withdrawal(memberKey, request.getPwd());
        return ApiResponse.ok(response);
    }

    @PostMapping("/check-email")
    public ApiResponse<Boolean> checkEmail(@RequestBody CheckEmailRequest request) {
        boolean result = memberQueryService.validationEmail(request.getEmail());
        return ApiResponse.ok(result);
    }


}
