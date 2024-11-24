package main.java.ru.valya.serveback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.matveyakulov.markoservcomeback.UserRole;
import ru.matveyakulov.markoservcomeback.dto.yandex.CompletionRequest;
import ru.matveyakulov.markoservcomeback.dto.yandex.CompletionResponse;

@RequiredArgsConstructor
@Service
public class GPTService {

    @Value("${application.yandex.gpt-url}")
    private String gptUrl;

    @Value("${application.yandex.api-key}")
    private String token;

    @Value("${application.yandex.folder-id}")
    private String folderId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String gpt(String text, UserRole userRole) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(
                new CompletionRequest(folderId, text, userRole)), getHeaders());
        return restTemplate.postForEntity(
                gptUrl + "/completion", entity, CompletionResponse.class)
                .getBody()
                .getResult().getAlternatives().get(0).getMessage().getText();

    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Api-Key " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
