package Swyp8.Team12.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDTO {
    private String nickname;

    public UserProfileResponseDTO(String nickname) {
        this.nickname = nickname;
    }
}

