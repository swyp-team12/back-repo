package Swyp8.Team12.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDTO {
    private String nickname;

    // 매개변수 생성자
    public UserProfileResponseDTO(String nickname) {
        this.nickname = nickname;
    }
}

