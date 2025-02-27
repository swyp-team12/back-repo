package Swyp8.Team12.domain.recipe.service;

import Swyp8.Team12.domain.recipe.dto.RecipeCreateResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClovaStudioService {

    @Value("${ncp.apiurl}")
    private String API_URL;

    @Value("${ncp.apikey}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RecipeService recipeService;

    public List<RecipeCreateResponseDTO> getRecipe(String userInput, Long userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 구성
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content",
                "- 입력한 재료에 적합한 요리와 레시피를 추천한다.\n" +
                        "- 레시피를 번호 순서대로 설명한다.\n" +
                        "- 필요한 재료를 적는다.\n" +
                        "- Json형식(\"name\",\"ingredient\",\"recipe\")으로 반환한다."));
        messages.add(Map.of("role", "user", "content", userInput));

        requestBody.put("messages", messages);
        requestBody.put("topP", 0.8);
        requestBody.put("topK", 0);
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.5);
        requestBody.put("repeatPenalty", 5.0);
        requestBody.put("includeAiFilters", true);
        requestBody.put("seed", 0);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("API 요청 실패: " + response.getStatusCode());
        }

        try {
            // JSON 응답을 Map으로 변환
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

            // 'result' 객체를 Map<String, Object>로 캐스팅하여 'message' 접근
            Map<String, Object> result = (Map<String, Object>) responseBody.get("result");
            Map<String, Object> message = (Map<String, Object>) result.get("message");
            String content = (String) message.get("content");

            // JSON 문자열을 List<Map<String, Object>>로 변환
            List<Map<String, Object>> recipes = objectMapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {});

            // List<RecipeResponseDTO>로 변환
            List<RecipeCreateResponseDTO> recipeList = new ArrayList<>();
            for (Map<String, Object> recipe : recipes) {
                RecipeCreateResponseDTO dto = new RecipeCreateResponseDTO();
                dto.setName((String) recipe.get("name"));
                dto.setIngredient((List<String>) recipe.get("ingredient"));
                dto.setRecipe((String) recipe.get("recipe"));
                recipeList.add(dto);
            }
            recipeService.save(recipeList, userId);
            return recipeList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
