package com.example.temp.member.domain;

import com.example.temp.follow.domain.FollowStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private FollowStrategy followStrategy;

    private boolean publicAccount;

    @Builder
    private Member(String email, String profileUrl, String nickname,
        FollowStrategy followStrategy, boolean publicAccount) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.followStrategy = followStrategy;
        this.publicAccount = publicAccount;
    }

    public FollowStatus getStatusBasedOnStrategy() {
        return followStrategy.getFollowStatus();
    }
}
