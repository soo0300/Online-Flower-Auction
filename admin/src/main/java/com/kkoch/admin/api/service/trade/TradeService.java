package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;

    public Long addTrade(Long memberId, List<AddTradeDto> dto) {
        //경매품 조회
        //경매품 -> 거래내역, 낙찰금액, 낙찰시간 업데이트
        //낙찰 내역 생성
        //경매품 -> 낙찰 내역 업데이트
        return null;
    }
}
