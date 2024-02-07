package org.example.springsecurity.repository;

import org.example.springsecurity.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    @Query("select t from Token t where t.user.id = :userId and t.expired = false and t.revoked = false")
    List<Token> findAllValidByUserId(Integer userId);

    Optional<Token> findByToken(String token);

}
