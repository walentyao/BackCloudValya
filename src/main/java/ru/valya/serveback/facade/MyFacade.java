package main.java.ru.valya.serveback.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matveyakulov.markoservcomeback.UserRole;
import ru.matveyakulov.markoservcomeback.domain.Request;
import ru.matveyakulov.markoservcomeback.dto.CompletionResponseDto;
import ru.matveyakulov.markoservcomeback.service.ExcelHelper;
import ru.matveyakulov.markoservcomeback.service.S3Service;
import ru.matveyakulov.markoservcomeback.service.GPTService;
import ru.matveyakulov.markoservcomeback.utils.Constant;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MyFacade {

    private final S3Service s3Service;
    private final GPTService GPTService;

    public CompletionResponseDto gpt(String text, UserRole userRole) {
        s3Service.getFile();
        String answer = GPTService.gpt(text, userRole);
        String path = ExcelHelper.write(Request.builder()
                .requestText(text)
                .answeredText(answer)
                .userRole(userRole.getValue())
                .build());
        s3Service.uploadFile(path);
        return new CompletionResponseDto(answer);
    }

    public List<Request> getRequests() {
        s3Service.getFile();
        return ExcelHelper.read(Constant.EXCEL_FILE_NAME);
    }
}
