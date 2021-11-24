package quizweb.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.service.impl.InsertTagServiceImpl.TagLists;

@SpringBootTest
public class InsertTagServiceTest {
    /*
     * HACK:mybatisの特定バージョンの仕様によってnameをつけないとMOCK化できない
     * 最新バージョンのSNAP-SHOTで解決されてるっぽいけどとりあえずnameの方で対応
     * https://stackoverflow.com/questions/62409666/mockbean-and-mybatis-mapper-not-
     * working-together-as-they-did-before-spring-boot
     */
    @MockBean(name = "tagMapper")
    TagMapper tagMapper;

    @Autowired
    InsertTagServiceImpl target;

    @Test
    public void testSeparateList() {
        List<Tag> existsTags = new ArrayList<>();

        Tag e1 = new Tag();
        e1.setTag("exists1");
        existsTags.add(e1);

        Tag e2 = new Tag();
        e2.setTag("exists2");
        existsTags.add(e2);

        Mockito.when(tagMapper.selectByTagContents(Mockito.any())).thenReturn(existsTags);

        List<String> argList = new ArrayList<>();
        argList.add("content2");
        argList.add("exists1");
        argList.add("exists2");
        TagLists result = target.separateTagList(argList);

        assertEquals(1, result.insertList.size());
        assertEquals(2, result.updateList.size());

        assertTrue(result.insertList.stream().anyMatch(t -> t.getTag().equals("content2")));

        assertTrue(result.updateList.stream().anyMatch(t -> t.getTag().equals("exists1")));
        assertTrue(result.updateList.stream().anyMatch(t -> t.getTag().equals("exists2")));

    }
}
