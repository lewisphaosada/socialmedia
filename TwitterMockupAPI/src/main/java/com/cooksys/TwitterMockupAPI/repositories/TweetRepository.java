package com.cooksys.TwitterMockupAPI.repositories;

import com.cooksys.TwitterMockupAPI.entities.Hashtag;
import com.cooksys.TwitterMockupAPI.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet t WHERE t.deleted = false ORDER BY t.posted DESC")
    List<Tweet> nonDeletedTweets();

    @Query("SELECT t FROM Tweet t WHERE t.id = :id AND t.deleted = false")
    Optional<Tweet> nonDeletedTweetsByID(Long id);

    @Query("SELECT t FROM Tweet t WHERE t.repostOf.id = :id AND t.deleted = false")
    List<Tweet> nonDeletedRepostsByID(Long id);


    @Query("SELECT t FROM Tweet t WHERE t.deleted = false AND t.id IN (SELECT parent.inReplyTo.id FROM Tweet parent WHERE parent.id = :id)")
    List<Tweet> findTweetsBefore(Long id);

    @Query("SELECT t FROM Tweet t WHERE t.deleted = false AND (t.inReplyTo.id = :id OR t.inReplyTo.id IN (SELECT r.id FROM Tweet r WHERE r.inReplyTo.id = :id))")
    List<Tweet> findRepliesAfter(Long id);


    List<Tweet> findByInReplyToIdAndDeletedFalse(Long id);


    List<Tweet> findByAuthor_IdAndDeletedFalseOrderByPostedDesc(Long id);


    List<Tweet> findByHashtagsContainingAndDeletedFalseOrderByPostedDesc(Hashtag hashtag);


}
