package com.kkoch.auction.api.service;

import com.kkoch.auction.api.controller.request.EventParticipant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class EventService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long EXPIRE_TIME_SECONDS = 60; // 유효 기간을 1분으로 설정

    public boolean joinEvent(EventParticipant participant) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(participant.getAuctionArticleId()))) {
            log.info("새로운 경매품: {}", participant.getAuctionArticleId());
        }
        log.info("경매품 {}의 참가자: {}", participant.getAuctionArticleId(), participant.getMemberToken());
        redisTemplate.opsForList().rightPush(participant.getAuctionArticleId(), participant.getMemberToken());
        redisTemplate.expire(participant.getAuctionArticleId(), EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);

        List<String> winner = redisTemplate.opsForList().range(participant.getAuctionArticleId(), 0, 0);
        if (winner == null) {
            return false;
        }
        log.info("경매품 {}의 1등: {}", participant.getAuctionArticleId(), winner.get(0));
        return !winner.isEmpty() && winner.get(0).equals(participant.getMemberToken());
    }
}
