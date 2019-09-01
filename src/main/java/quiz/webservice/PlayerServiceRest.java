package quiz.webservice;

import quiz.entity.Player;
import quiz.service.Exception.PlayerServiceException;
import quiz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;

@Path("/player")
public class PlayerServiceRest {

    @Autowired
    private PlayerService playerService;

    //http://localhost:8080/rest/player
    @POST
    @Consumes("application/json")
    public void register(Player player) throws PlayerServiceException {
        playerService.register(player);
    }

    @POST
    @Path("/{id}/{score}")
    @Consumes("application/json")
    public void setPlayerScore(@PathParam("id") int id, @PathParam("score") int score) throws PlayerServiceException {
        playerService.setPlayerScore(id, score);
    }

    @GET
    @Path("/allPlayers")
    @Produces("application/json")
    public List<Player> getAllPlayers() throws PlayerServiceException {
        return playerService.getAllPlayers();
    }


    @GET
    @Path("/getPlayerFromID/{id}")
    @Produces("application/json")
    public Player getPlayerFromID(@PathParam("id") int id) throws PlayerServiceException {
        return playerService.getPlayerFromID(id);
    }
}
