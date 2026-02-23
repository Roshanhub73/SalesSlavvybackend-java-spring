package com.kodnest.project.app.USER;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface JWTTokenRepository extends JpaRepository<tokens,Integer>{
@Query("select t from tokens t where t.user.userid= :userid")
tokens findByUserId(@Param("userid") int userid);

Optional<tokens>findByToken(String token);
}
