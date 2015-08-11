package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/finalize")
    public void finalizeWebhook() {
        Greeting greeting = new Greeting("Hello, " + System.currentTimeMillis() + "!");
        String message;
        try {
            message = objectMapper.writeValueAsString(greeting);
        } catch (JsonProcessingException e) {
            message = "Error?";
        }
        template.convertAndSend("/topic/greetings", message);

    }
}
