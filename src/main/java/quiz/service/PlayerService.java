package quiz.service;

import quiz.entity.Player;
import quiz.service.Exception.PlayerServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerService {

    void register(Player player) throws PlayerServiceException;

    void setPlayerScore(int id, int score) throws PlayerServiceException;

    List<Player> getAllPlayers() throws PlayerServiceException;

    Player getPlayerFromMail(String mail);

    Player getPlayerFromID(int id) throws PlayerServiceException;

    Player login(String email, String password) throws PlayerServiceException;

}
