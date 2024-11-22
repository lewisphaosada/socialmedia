package com.cooksys.TwitterMockupAPI.entities;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table(name = "tweets")
@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

	@ManyToOne
	private User author;

    @CreationTimestamp
    private Timestamp posted;

    private boolean deleted = false;
    
    private String content;

    //many tweets can reply to one tweet (parent tweet)
    @ManyToOne
    private Tweet inReplyTo;


    //inReplyTo: a tweet can have many replies (child tweets)
    @OneToMany(mappedBy="inReplyTo")
    private List<Tweet> replies; 

    //many tweets can repost one tweet (parent tweet)
    @ManyToOne
    private Tweet repostOf;

    //repostOf: One tweet can have many reposts (child tweets)
    @OneToMany(mappedBy="repostOf")
    private List<Tweet> reposts;

    //one tweet can be liked by many users
    @ManyToMany(mappedBy="likedTweets")
    private List<User> likedByUsers;


    // //one tweet can be mentioned by many users
     @ManyToMany
     @JoinTable(
    		 name = "user_mentions", 
    		 joinColumns = @JoinColumn(name = "tweet_id"), 
    		 inverseJoinColumns = @JoinColumn(name = "user_id")
    		 )
     private List<User> mentionedUsers;

    // //one tweet can have many hashtags
    @ManyToMany
    @JoinTable(
    		name = "tweet_hashtags", 
    		joinColumns = @JoinColumn(name = "tweet_id"), 
    		inverseJoinColumns= @JoinColumn(name = "hashtag_id")
    		)
    private List<Hashtag> hashtags;

}
