package com.kkoch.user.domain.member.repository;

import com.kkoch.user.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
