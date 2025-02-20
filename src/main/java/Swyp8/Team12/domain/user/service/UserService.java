package Swyp8.Team12.domain.user.service;

import Swyp8.Team12.domain.user.dto.KakaoUserInfoDTO;
import Swyp8.Team12.domain.user.dto.UserProfileResponseDTO;
import Swyp8.Team12.domain.user.dto.UserProfileUpdateRequestDTO;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.repository.UserRepository;
import Swyp8.Team12.global.security.jwt.JwtCookieUtil;
import Swyp8.Team12.global.security.jwt.JwtTokenProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtCookieUtil jwtCookieUtil;

    public ResponseCookie createAccessTokenCookie(Long userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        return jwtCookieUtil.createAccessTokenCookie(accessToken);
    }

    public User kakaoSave(KakaoUserInfoDTO kakaoUserInfoDto) {
        return userRepository.findBySocialId(String.valueOf(kakaoUserInfoDto.getSocialId()))
                .orElseGet(() -> {
                    User newUser = User.kakaoUserBuilder()
                            .socialId(String.valueOf(kakaoUserInfoDto.getSocialId()))
                            .email(kakaoUserInfoDto.getEmail())
                            .nickname(kakaoUserInfoDto.getNickname())
                            .profileImg(kakaoUserInfoDto.getProfileImage())
                            .build();
                    return userRepository.save(newUser);
                });
    }

    public void updateUserProfile(Long userId, UserProfileUpdateRequestDTO userProfileUpdateRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        user.updateProfile(userProfileUpdateRequestDTO.getNickname());
        userRepository.save(user);
    }

    public UserProfileResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        return new UserProfileResponseDTO(user.getNickname());
    }

}
