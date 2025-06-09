package sn.fhunHospital.patient_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "X-GitHub-Event", required = false) String githubEvent) {

        logger.info("=== WEBHOOK REÇU ===");
        logger.info("Event: {}", githubEvent);
        logger.info("Payload: {} caractères", payload.length());

        return ResponseEntity.ok("Webhook traité avec succès !");
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> webhookHealthCheck() {
        return ResponseEntity.ok("Endpoint webhook actif !");
    }
}