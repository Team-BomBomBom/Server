package com.bombombom.devs.external.algo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

import com.bombombom.devs.core.enums.AlgoTag;
import com.bombombom.devs.external.algo.config.ProbabilityConfig;
import java.util.Map;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AlgorithmProblemServiceTest {

    @Mock
    private RandomGenerator randomGenerator;

    @InjectMocks
    private AlgorithmProblemService algorithmProblemService;


    @Test
    @DisplayName("각 태그마다 정해진 추첨 스프레드를 가지고 해당 스프레드는 누적합을 통해 계산된다.")
    void drawProblem() {
        /*
         * Given
         */
        final int totalProblemCount = 1;
        ProbabilityConfig.totalProbability = 1.0;
        ProbabilityConfig config = new ProbabilityConfig();
        ReflectionTestUtils.setField(config, "math", 1.0);
        ReflectionTestUtils.setField(config, "dp", 1.0);
        ReflectionTestUtils.setField(config, "greedy", 1.0);
        ReflectionTestUtils.setField(config, "impl", 1.0);
        ReflectionTestUtils.setField(config, "graph", 1.0);
        ReflectionTestUtils.setField(config, "geometry", 1.0);
        ReflectionTestUtils.setField(config, "ds", 1.0);
        ReflectionTestUtils.setField(config, "string", 1.0);
        config.init();


        /*
         * When (Random Double = 0.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(0.0);
        Map<AlgoTag, Integer> result0 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result0).isNotNull();
        assertThat(result0.get(AlgoTag.MATH)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 1.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(1.0);
        Map<AlgoTag, Integer> result1 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result1).isNotNull();
        assertThat(result1.get(AlgoTag.DP)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 2.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(2.0);
        Map<AlgoTag, Integer> result2 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result2).isNotNull();
        assertThat(result2.get(AlgoTag.GREEDY)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 3.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(3.0);
        Map<AlgoTag, Integer> result3 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result3).isNotNull();
        assertThat(result3.get(AlgoTag.IMPLEMENTATION)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 4.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(4.0);
        Map<AlgoTag, Integer> result4 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result4).isNotNull();
        assertThat(result4.get(AlgoTag.GRAPHS)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 5.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(5.0);
        Map<AlgoTag, Integer> result5 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result5).isNotNull();
        assertThat(result5.get(AlgoTag.GEOMETRY)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 6.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(6.0);
        Map<AlgoTag, Integer> result6 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result6).isNotNull();
        assertThat(result6.get(AlgoTag.DATA_STRUCTURES)).isEqualTo(totalProblemCount);

        /*
         * When (Random Double = 7.0)
         */
        when(randomGenerator.nextDouble(anyDouble())).thenReturn(7.0);
        Map<AlgoTag, Integer> result7 =
            algorithmProblemService.getProblemCountForEachTag(totalProblemCount);

        /*
         * Then
         */
        assertThat(result7).isNotNull();
        assertThat(result7.get(AlgoTag.STRING)).isEqualTo(totalProblemCount);
    }
}