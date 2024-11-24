package main.java.ru.valya.serveback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matveyakulov.markoservcomeback.UserRole;
import ru.matveyakulov.markoservcomeback.domain.Request;
import ru.matveyakulov.markoservcomeback.dto.CompletionResponseDto;
import ru.matveyakulov.markoservcomeback.facade.MyFacade;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MyController {

    private final MyFacade facade;

    @GetMapping("/gpt")
    public CompletionResponseDto gpt(@RequestParam String text, @RequestParam UserRole userRole) {
        return facade.gpt(text, userRole);
    }

    @GetMapping("/roles")
    public List<UserRole> getRoles() {
        return Arrays.stream(UserRole.values()).toList();
    }

    @GetMapping("/requests")
    public List<Request> getRequests() {
        return facade.getRequests();
    }

}
