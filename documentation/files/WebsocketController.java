package quiz.controller;

import quiz.core.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @MessageMapping("/message/{controllerId}")
    @SendTo("/controller/{controllerId}")
    public Message process(@DestinationVariable String controllerId, Message message) throws Exception {
        return message;
    }
}
