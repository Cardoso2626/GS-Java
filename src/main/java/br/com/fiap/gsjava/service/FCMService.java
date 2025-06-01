package br.com.fiap.gsjava.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FCMService {
    private final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final String SERVER_KEY = "SUA_CHAVE_SECRETA_DO_FIREBASE"; // Use a chave do console Firebase
    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarNotificacao(String tokenFCM, String titulo, String corpo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + SERVER_KEY);

        Map<String, Object> notificacao = Map.of(
                "to", tokenFCM,
                "notification", Map.of(
                        "title", titulo,
                        "body", corpo
                )
        );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(notificacao, headers);

        try {
            restTemplate.postForEntity(FCM_API_URL, request, String.class);
            System.out.println("Notificação enviada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
