package ru.frank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_score")
public class UserScore implements Serializable, Comparable<UserScore>{

    private static final long serialVersionUID = 8933332456626130380L;

    @Id
    @Column(name = "id")
    private long id;

    @Column
    private String userName;

    @Column(name = "score")
    private long score;

    public UserScore(long id, String userName, long score) {
        this.id = id;
        this.userName = userName;
        this.score = score;
    }

    public UserScore() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int compareTo(UserScore userScore) {
        return (int) (this.score - userScore.getScore());
    }

    @Override
    public String toString() {
        return "UserScore{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
