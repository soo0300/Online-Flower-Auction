package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
