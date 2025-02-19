package Swyp8.Team12.domain.user.controller;

import Swyp8.Team12.domain.user.dto.KakaoUserInfoDTO;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.service.KakaoService;
import Swyp8.Team12.domain.user.service.UserService;
import Swyp8.Team12.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    /**
     *
     * 카카오 로그인 & 회원가입
     *
     **/
    @GetMapping("/kakao/callback")
    ResponseEntity<ApiResponse<?>> kakaoLogin(@RequestParam String code) {

        String kakaoToken = kakaoService.getKakaoToken(code);
        KakaoUserInfoDTO kakaoUserInfoDto = kakaoService.getKakaoUserInfo(kakaoToken);
        User user = userService.kakaoSave(kakaoUserInfoDto);
        ResponseCookie accessTokenCookie = userService.createAccessTokenCookie(user.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .body(ApiResponse.successWithMessage("로그인 성공"));
    }
}
