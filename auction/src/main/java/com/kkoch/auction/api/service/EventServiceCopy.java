//package com.kkoch.auction.api.service;
//
//import com.kkoch.auction.api.controller.request.EventParticipant;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Service
//@Transactional(readOnly = true)
//@Slf4j
//public class EventServiceCopy {
//
//    private final RedisTemplate<String, Object> redisTemplate;
////    private final HashOperations<String, Object, Object> redisHash;
//
////    private ListOperations<String, Object> redisList;
//
////    @Autowired
////    public EventService(RedisTemplate<String, Object> redisTemplate) {
////        this.redisTemplate = redisTemplate;
//////        this.redisHash = redisTemplate.opsForHash();
////
////    }
////
////    @PostConstruct
////    public void test() {
////        this.redisList = redisTemplate.opsForList();
////    }
//
////    private final RedisTemplate<String, BidScore> getFirst;
////    private final RedisTemplate<String, String> getNumber;
//    private static final long EXPIRE_TIME_SECONDS = 60; // 유효 기간을 1분으로 설정
////    private static final long USER_NUMBER_EXPIRE_TIME_SECONDS = 600; // 유효 기간을 1분으로 설정
//
//
//    public boolean joinEvent(EventParticipant participant) {
//
//
//
//        if (Boolean.FALSE.equals(redisTemplate.hasKey(participant.getAuctionArticleId()))) {
//            log.info("새로운 경매품: {}", participant.getAuctionArticleId());
//        }
//        log.info("경매품 {}의 참가자: {}", participant.getAuctionArticleId(), participant.getMemberKey());
//        redisTemplate.opsForList().rightPush(participant.getAuctionArticleId(), getBidScore(participant));
//        redisTemplate.expire(participant.getAuctionArticleId(), EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
//
////        getNumber.opsForSet().add("userNumber", participant.getMemberKey());
////        getFirst.expire("userNumber", USER_NUMBER_EXPIRE_TIME_SECONDS, TimeUnit.SECONDS);
//
//        List<Object> winner = redisTemplate.opsForList().range(participant.getAuctionArticleId(), 0, 0);
//        if (winner == null) {
//            return false;
//        }
//        BidScore bidWinner = (BidScore) winner.get(0);
//        log.info("경매품 {}의 1등: {}", participant.getAuctionArticleId(), bidWinner.getMemberKey());
//        return !winner.isEmpty() && bidWinner.getMemberKey().equals(participant.getMemberKey());
//    }
//
////    public int getMemberNumber(String memberKey) {
////        if (!getNumber.opsForHash().hasKey("userNumber", memberKey)) {
////            int newNumber = getNumber.opsForHash().size("userNumber").intValue() + 1;
////            getNumber.opsForHash().put("userNumber", memberKey, newNumber);
////            return newNumber;
////        }
////        return (Integer) getNumber.opsForHash().get("userNumber", memberKey);
////    }
//
////    public int getPrice(@NotNull String auctionArticleId) {
////        List<BidScore> winner = getFirst.opsForList().range(auctionArticleId, 0, 0);
////        return winner.get(0).getPrice();
////    }
//
////    public int getWinner(String auctionArticleId) {
////        List<BidScore> winner = getFirst.opsForList().range(auctionArticleId, 0, 0);
////        String winnerKey = winner.get(0).getMemberKey();
////        return (int) getFirst.opsForHash().get("userNumber", winnerKey);
////    }
//
//    private static BidScore getBidScore(EventParticipant participant) {
//        return BidScore.builder()
//                .memberKey(participant.getMemberKey())
//                .price(participant.getPrice())
//                .build();
//    }
//
//}
