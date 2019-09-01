package quiz.service;


import quiz.entity.Player;
import quiz.service.Exception.PlayerServiceException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Component
public class PlayerServiceJPA implements PlayerService {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * The method registers the player, and throws an exception if an error occurred
     * @param player
     * @throws PlayerServiceException
     */
    @Override
    public void register(Player player) throws PlayerServiceException {
        try {
            entityManager.createNamedQuery("Player.login").setParameter("email", player.getEmail()).setParameter("password", player.getPassword()).getSingleResult();
            throw new PlayerServiceException("The player already exists!");
        } catch (NoResultException e) {
            entityManager.persist(player);
            return;
        }
    }



    /**
     * Sets player score
     * @param id, score
     */
    @Override
    public void setPlayerScore(int id, int score) throws PlayerServiceException {
        Player player = this.getPlayerFromID(id);
        player.setScore(score);
    }


    /**
     * Returns a list of players
     * Can be used in hal of fame
     * @return List<Player>
     * @throws PlayerServiceException
     */
    @Override
    public List<Player> getAllPlayers() throws PlayerServiceException {
        try {
            return (List<Player>) entityManager.createQuery("SELECT p FROM Player p").getResultList();
        } catch (NoResultException e) {
            throw new PlayerServiceException("Can't load players from database", e);
        }
    }

    @Override
    public Player getPlayerFromMail(String mail) {
        return (Player) entityManager.createNamedQuery("Player.GetPlayerFromEmail").setParameter("email", mail).getSingleResult();
    }


    /**
     * Return player by his id
     * @param id
     * @return Player
     */

    @Override
    public Player getPlayerFromID(int id) throws PlayerServiceException {
        try {
            return (Player) entityManager.createNamedQuery("Player.GetPlayerID").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new PlayerServiceException("The player with id = " + id + "dose'n exist!", e);
        }
    }


    /**
     * Method returns a player using his email and validates password
     * @param email
     * @param password
     * @return Player
     * @throws PlayerServiceException
     */
    @Override
    public Player login(String email, String password) throws PlayerServiceException {
        try {
            Player player = (Player) entityManager.createNamedQuery("Player.login").setParameter("email", email).setParameter("password", password).getSingleResult();
            return player;
        } catch (Exception e) {
            throw new PlayerServiceException("The player does't exist!", e);
        }


    }

}
