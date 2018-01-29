package ru.frank.dataBaseUtil.userScore;

import org.springframework.stereotype.Component;
import ru.frank.model.UserScore;

import java.util.List;

@Component
public interface UserScoreDao {

    UserScore get(long id);

    List<UserScore> getAll();

    long save(UserScore userScore);

    void update(UserScore userScore);
}
