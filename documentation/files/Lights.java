package quiz.core;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Arrays;

public class Lights implements Serializable {

    private String[] colors = new String[97];

    private int duration = 500;

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    //Color is read or green
    public void turnOn(String color) {
        switch (color) {
            case "red":
                color = "FF000000";
                break;
            case "green":
                color = "00FF0000";
                break;
            default:
                color = "FFFFFF00";
        }
        for (int i = 0; i < 97; i++) {
            colors[i] = color;
        }

        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target("https://openlab.kpi.fei.tuke.sk/rest/light")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(this, MediaType.APPLICATION_JSON), Response.class);
            System.out.println(">>>>> " + response.getHeaders());
            System.out.println(">>> " + response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("chyba: " + e);
        }
    }
}