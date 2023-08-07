package com.kkoch.user.domain.member.repository;

import com.kkoch.user.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("select m.id from Member m where m.email =:email")
    Optional<Long> findIdByEmail(@Param("email") String email);

    @Query("select m from Member m where m.memberKey =: memberKey")
    Optional<Member> findByMemberKey(@Param("memberKey") String memberKey);
}
