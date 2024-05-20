package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuavaLearningTest {

    @DisplayName("주어진 개수만큼 list를 파티셔닝 한다.")
    @Test
    void partitionLearningTest1(){
        // Given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // When
        List<List<Integer>> partition = Lists.partition(integers, 3);
        // Then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3),
                        List.of(4,5,6)
                ));


    }

    @DisplayName("주어진 개수만큼 list를 파티셔닝 한다.")
    @Test
    void partitionLearningTest2(){
        // Given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // When
        List<List<Integer>> partition = Lists.partition(integers, 4);
        // Then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3,4),
                        List.of(5,6)
                ));


    }

    @DisplayName("멀티맵 기능 확인")
    @Test
    void multimapLearningTest(){
        // Given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피","아메리카노");
        multimap.put("커피","카페라떼");
        multimap.put("커피","카푸치노");
        multimap.put("베이커리","크루아상");
        multimap.put("베이커리","식빵");

        // When
        Collection<String> strings = multimap.get("커피");

        // Then
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of("아메리카노","카페라떼","카푸치노"));
    }

    @DisplayName("멀티맵 기능 확인")
    @TestFactory
    Collection<DynamicTest> multimapLearningTest2(){
        // Given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피","아메리카노");
        multimap.put("커피","카페라떼");
        multimap.put("커피","카푸치노");
        multimap.put("베이커리","크루아상");
        multimap.put("베이커리","식빵");

        return List.of(
                DynamicTest.dynamicTest("1개 벨류 삭제", ()->{
                    // when
                    multimap.remove("커피","아메리카노");
                    // then
                    Collection<String> strings = multimap.get("커피");
                    assertThat(strings).hasSize(2)
                            .isEqualTo(List.of("카페라떼","카푸치노"));
                }),
                DynamicTest.dynamicTest("키에 대한 일괄 삭제",()->{
                    // when
                    multimap.removeAll("베이커리");
                    //then
                    Collection<String> strings = multimap.get("베이커리");
                    assertThat(strings).hasSize(0)
                            .isEmpty();
                })
        );

    }
}
