package com.kkoch.user.api.controller.wishlist.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddWishlistRequest {

    private Long auctionArticleId;
}