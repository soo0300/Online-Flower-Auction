package com.kkoch.admin.domain.trade.repository;

import com.kkoch.admin.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}
