package com.kkoch.user.api.controller.wishlist.response;

import lombok.Data;

@Data
public class WishlistResponse {

    private Long wishlistId;
    private Long auctionArticleId;
    private String code;
    private String type;
    private String name;
    private String grade;
    private String region;
    private int count;
}
