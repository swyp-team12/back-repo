package Swyp8.Team12.domain.user.controller;

import Swyp8.Team12.domain.user.dto.KakaoUserInfoDTO;
import Swyp8.Team12.domain.user.dto.UserProfileResponseDTO;
import Swyp8.Team12.domain.user.dto.UserProfileUpdateRequestDTO;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.service.KakaoService;
import Swyp8.Team12.domain.user.service.UserService;
import Swyp8.Team12.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
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

        return ResponseEntity.status(HttpStatus.FOUND)  // 302 Found
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.LOCATION, "http://localhost:3000/home")  // 프론트엔드 리다이렉트 URL -> 배포 시에 도메인으로 수정
                .body(ApiResponse.successWithMessage("로그인 성공"));
    }

    /**
     *
     * 내 프로필 조회
     *
     **/
    @GetMapping("profile")
    ResponseEntity<ApiResponse<?>> getUserProfile(@AuthenticationPrincipal Long userId){
        UserProfileResponseDTO userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok().body(ApiResponse.successResponse(userProfile));
    }

    /**
     *
     * 내 프로필 업데이트
     *
     **/
    @PatchMapping("profile")
    ResponseEntity<ApiResponse<?>> updateUserProfile(@AuthenticationPrincipal Long userId,
                                                     @RequestBody UserProfileUpdateRequestDTO userProfileUpdateRequestDTO
    ){
        userService.updateUserProfile(userId, userProfileUpdateRequestDTO);
        return ResponseEntity.ok().body(ApiResponse.successWithMessage("프로필 업데이트 성공"));
    }   
}
