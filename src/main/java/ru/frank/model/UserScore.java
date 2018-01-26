package ru.frank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_score")
public class UserScore {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "score")
    private long score;

    public UserScore(long id, long score) {
        this.id = id;
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
}
