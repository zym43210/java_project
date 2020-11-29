package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Collection<Comment>> getCommentByConcertId(Long concertId);
    Optional<Collection<Comment>> getCommentsByConcertId(Long concertId);
    void deleteCommentsByConcertId(Long concertId);
}
