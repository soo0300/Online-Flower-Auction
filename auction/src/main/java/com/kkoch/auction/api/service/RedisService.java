package com.kkoch.auction.api.service;

import com.kkoch.auction.api.controller.request.EventParticipant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long EXPIRE_TIME_SECONDS = 60; // 유효 기간을 1분으로 설정
    private static final long USER_NUMBERS_EXPIRE_TIME_SECONDS = 600; // 유효 기간을 10분으로 설정
    private static final String USER_NUMBERS = "userNumbers";

    public int getNumber(String memberKey) {
        HashOperations<String, String, String> redisHash = redisTemplate.opsForHash();

        if (redisHash.hasKey(USER_NUMBERS, memberKey)) {
            String currMember = redisHash.get(USER_NUMBERS, memberKey);
            redisTemplate.expire(USER_NUMBERS, USER_NUMBERS_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
            return Integer.parseInt(currMember);
        }
        String newNumber = String.valueOf(redisHash.size(USER_NUMBERS) + 1);
        log.info("{} 번호 발급 : {}", memberKey, newNumber);
        redisHash.put(USER_NUMBERS, memberKey, newNumber);
        redisTemplate.expire(USER_NUMBERS, USER_NUMBERS_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
        return Integer.parseInt(newNumber);
    }

    public boolean joinEvent(EventParticipant participant) {
        String auctionArticleId = participant.getAuctionArticleId();
        String memberKey = participant.getMemberKey();
        String price = String.valueOf(participant.getPrice());

        if (Boolean.FALSE.equals(redisTemplate.hasKey(auctionArticleId))) {
            log.info("새로운 경매품: {}", auctionArticleId);
        }

        Bidding(auctionArticleId, memberKey, price);

        List<String> winner = redisTemplate.opsForList().range(auctionArticleId, 0, 0);
        if (winner == null) {
            return false;
        }
        log.info("경매품 {}의 1등: {}", auctionArticleId, winner);
        return !winner.isEmpty() && winner.get(0).equals(memberKey);
    }

    private void Bidding(String auctionArticleId, String memberKey, String price) {
        log.info("경매품 {}의 참가자: {}", auctionArticleId, memberKey);

        redisTemplate.opsForList().rightPush(auctionArticleId, memberKey);
        redisTemplate.expire(auctionArticleId, EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);

        HashOperations<String, String, String> redisHash = redisTemplate.opsForHash();
        String priceKey = getPriceKey(auctionArticleId);
        if (redisHash.get(priceKey, memberKey) == null) {
            redisHash.put(priceKey, memberKey, price);
        }
        redisTemplate.expire(priceKey, EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
    }

    private static String getPriceKey(String auctionArticleId) {
        return "price-" + auctionArticleId;
    }

    public String getWinnerKey(String auctionArticleId) {
        return redisTemplate.opsForList().index(auctionArticleId, 0);
    }

    public String getWinnerPrice(String priceKey, String winnerKey) {
        HashOperations<String, String, String> redisHash = redisTemplate.opsForHash();
        return redisHash.get(priceKey, winnerKey);
    }
}
