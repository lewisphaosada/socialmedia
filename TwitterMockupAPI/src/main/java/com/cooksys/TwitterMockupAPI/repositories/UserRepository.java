package com.cooksys.TwitterMockupAPI.repositories;

import com.cooksys.TwitterMockupAPI.entities.Tweet;
import com.cooksys.TwitterMockupAPI.entities.User;
import com.cooksys.TwitterMockupAPI.entities.embeddables.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByCredentials(Credentials credentials);

    Optional<User> findByCredentialsUsername(String username);

    @Query("Select u from User u where u.deleted = false")
    List<User> getActiveUsers();

    boolean existsByCredentialsUsername(String username);

    List<User> findByFollowersIdAndDeletedFalse(Long id);

    List<User> findByFollowingIdAndDeletedFalse(Long id);

@Query("Select users from User users join users.mentionedUsers mu where mu.id = :id and users.deleted = false")
    List<User> findMentions(Long id);

    @Query("SELECT u FROM User u JOIN u.likedTweets t WHERE t.id = :id AND u.deleted = false")
    List<User> findUsersByLikedTweet(Long id);
//also possible to do above with derive List<User> findByMentionedUsers_IdAndDeletedFalse(Long id)

    @Query("SELECT t FROM Tweet t WHERE t.deleted = false AND t.content LIKE %:username% ORDER BY t.posted DESC")
    List<Tweet> findMentions(@Param("username") String username);
}
