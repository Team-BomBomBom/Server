package com.bombombom.devs.client.solvedac;

import com.bombombom.devs.client.solvedac.dto.ProblemListResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class SolvedacClient {

    private static final String BASE_URL = "https://solved.ac/api/v3";
    private static final String SEARCH_PROBLEM_PATH = "/search/problem";
    private static final String USER_PREFIX = "!@";
    private static final String TAG_PREFIX = "#";
    private static final String DIFFICULTY_PREFIX = "*";
    private static final String DIFFICULTY_GAP = "..";
    private static final String SPACE = " ";

    public ProblemListResponse getUnSolvedProblems(
        List<String> baekjoonIds,
        Map<String, Integer> problemCountForEachTag,
        Map<String, Pair<Integer, Integer>> difficultySpreadForEachTag
    ) {
        ProblemListResponse unSolvedProblems = new ProblemListResponse(new ArrayList<>());
        WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();
        for (String tag : problemCountForEachTag.keySet()) {
            Integer numberOfProblems = problemCountForEachTag.get(tag);
            Pair<Integer, Integer> difficultySpread = difficultySpreadForEachTag.get(tag);
            ProblemListResponse problemsByTag = getUnSolvedProblemsByTag(
                webClient,
                baekjoonIds,
                tag,
                difficultySpread
            );
            log.debug("problemsByTag.items.size: {}", problemsByTag.items().size());
            unSolvedProblems.items().addAll(problemsByTag.items().subList(0, numberOfProblems));
        }
        return unSolvedProblems;
    }

    private ProblemListResponse getUnSolvedProblemsByTag(
        WebClient webClient,
        List<String> baekjoonIds,
        String tag,
        Pair<Integer, Integer> difficultySpread
    ) {
        CompletableFuture<ProblemListResponse> completableFuture = new CompletableFuture<>();
        String queryParam = makeGetUnSolvedProblemsQueryParams(baekjoonIds, tag, difficultySpread);
        log.debug("getUnSolvedProblemsByTag() Query: {}", queryParam);
        Mono<ProblemListResponse> mono = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(SEARCH_PROBLEM_PATH)
                .queryParam("query", queryParam)
                .queryParam("sort", "solved")
                .queryParam("direction", "desc")
                .queryParam("page", 1)
                .build()
            )
            .retrieve()
            .bodyToMono(ProblemListResponse.class);

        mono.subscribe(
            response -> {
                log.debug("getUnSolvedProblems() Response: {}", response);
                completableFuture.complete(response);
            },
            error -> log.error("Error during getUnSolvedProblems: " + error.getMessage())
        );
        return completableFuture.join();
    }

    private String makeGetUnSolvedProblemsQueryParams(
        List<String> baekjoonIds,
        String tag,
        Pair<Integer, Integer> difficultySpread
    ) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("s");
        for (String id : baekjoonIds) {
            queryBuilder
                .append(USER_PREFIX)
                .append(id);
        }
        queryBuilder
            .append(SPACE)
            .append(TAG_PREFIX)
            .append(tag)
            .append(SPACE)
            .append(DIFFICULTY_PREFIX)
            .append(difficultySpread.getFirst())
            .append(DIFFICULTY_GAP)
            .append(difficultySpread.getSecond());
        return queryBuilder.toString();
    }

}