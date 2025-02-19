package Swyp8.Team12.domain.user.service;

import Swyp8.Team12.domain.user.dto.KakaoUserInfoDTO;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.repository.UserRepository;
import Swyp8.Team12.global.security.jwt.JwtCookieUtil;
import Swyp8.Team12.global.security.jwt.JwtTokenProvider;
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
}
