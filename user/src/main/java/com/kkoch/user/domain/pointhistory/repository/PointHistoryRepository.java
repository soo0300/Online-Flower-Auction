package com.kkoch.user.domain.pointhistory.repository;

import com.kkoch.user.domain.pointhistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
