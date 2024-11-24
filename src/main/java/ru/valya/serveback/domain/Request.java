package main.java.ru.valya.serveback.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request {

    private String requestText;
    private String answeredText;
    private String userRole;
}
