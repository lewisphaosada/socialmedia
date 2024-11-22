package com.cooksys.TwitterMockupAPI.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.TwitterMockupAPI.entities.embeddables.Credentials;
import com.cooksys.TwitterMockupAPI.entities.embeddables.Profile;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Timestamp joined;

    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;
    
    @OneToMany(mappedBy="author")
    private List<Tweet> tweets;

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentionedUsers;

    @ManyToMany
    @JoinTable(name="user_likes",
    			joinColumns = @JoinColumn(name="user_id"),
    			inverseJoinColumns = @JoinColumn(name="tweet_id")
    		)
    private List<Tweet> likedTweets;

    @ManyToMany
    @JoinTable(name="followers_following")
    private List<User> followers;
    
    @ManyToMany(mappedBy="followers")
    private List<User> following;

}
