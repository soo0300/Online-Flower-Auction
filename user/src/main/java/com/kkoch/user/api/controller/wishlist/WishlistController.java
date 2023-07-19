package com.kkoch.user.api.controller.wishlist;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.wishlist.request.AddWishlistRequest;
import com.kkoch.user.api.controller.wishlist.response.WishlistResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/wishlist")
@Slf4j
@Api(tags = {"위시리스트 기능"})
public class WishlistController {

    // 위시리스트 등록
    @ApiOperation(value = "위시리스트 등록")
    @PostMapping
    public ApiResponse<?> addWishlist(@RequestBody AddWishlistRequest request) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }

    // 위시리스트 조회
    @ApiOperation(value = "위시리스트 조회")
    @GetMapping
    public ApiResponse<List<WishlistResponse>> getWishlist() {
        return ApiResponse.ok(null);
    }

    // 위시리스트 삭제
    @ApiOperation(value = "위시리스트 삭제")
    @DeleteMapping("/{wishlistId}")
    public ApiResponse<?> removeWishlist(@PathVariable Long wishlistId) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }
}
