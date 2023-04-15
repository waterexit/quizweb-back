package quizweb.repository.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.TagMapper;

@SpringBootTest
public class TagMapperTest {
    @Autowired
    TagMapper tagMapper;

    @Test
    @Transactional
    public void insertTest() {
        List<Tag> tagList = new ArrayList<>();
        Tag t1 = new Tag();
        t1.setNum(1l);
        t1.setTag("test");
        tagList.add(t1);

        Tag t2 = new Tag();
        t2.setNum(1l);
        t2.setTag("test2");
        tagList.add(t2);

        int resulNum = tagMapper.insertList(tagList);

        assertEquals(2, resulNum);

    }

    @Test
    @Transactional
    public void selectTest() {
        List<Tag> tagList = new ArrayList<>();
        Tag t1 = new Tag();
        t1.setNum(1l);
        t1.setTag("test");
        t1.setNum(1l);
        tagList.add(t1);

        Tag t2 = new Tag();
        t2.setNum(1l);
        t2.setTag("test2");
        t2.setNum(2l);
        tagList.add(t2);

        tagMapper.insertList(tagList);

        List<Tag> resultList = tagMapper.selectByPrefix("test");
        // test to prefix search
        assertEquals(2, resultList.size());

        // test to orderge
        assertEquals("test2", resultList.get(0).getTag());
    }

    @Test
    @Transactional
    public void limiTest() {
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Tag t1 = new Tag();
            t1.setTag("test");
            t1.setNum(1l);
            tagList.add(t1);
        }

        tagMapper.insertList(tagList);

        List<Tag> resultList = tagMapper.selectByPrefix("test");
        // test to limit
        assertEquals(10, resultList.size());
    }

    @Test
    @Transactional
    public void updateNumTest() {
        List<Tag> tagList = new ArrayList<>();
        Tag t1 = new Tag();
        t1.setTag("num_test");
        t1.setNum(1l);
        tagList.add(t1);

        tagMapper.insertList(tagList);

        // tagMapper.updateNum();

        // test to update
        assertEquals(2, tagMapper.selectByPrimaryKey(t1.getId()).getNum());
    }

    @Test
    @Transactional
    public void selectByTagContentsTest() {
        List<Tag> insertTagList = new ArrayList<>();
        Tag t1 = new Tag();
        t1.setTag("content1");
        t1.setNum(1l);
        insertTagList.add(t1);

        Tag t2 = new Tag();
        t2.setTag("content2");
        t2.setNum(1l);
        insertTagList.add(t2);

        Tag t3 = new Tag();
        t3.setTag("content3");
        t3.setNum(1l);
        insertTagList.add(t3);

        tagMapper.insertList(insertTagList);

        List<String> selectTagList = new ArrayList<>();
        selectTagList.add("content1");
        selectTagList.add("content3");

        // List<Tag> resultList = tagMapper.selectByTagContents(selectTagList);
        // test to select by tagContent
        // assertEquals(2, resultList.size());
    }

    @Test
    @Transactional
    public void selectByTagContentsTestEmptyCase() {
        List<Tag> insertTagList = new ArrayList<>();
        Tag t1 = new Tag();
        t1.setTag("content1");
        t1.setNum(1l);
        insertTagList.add(t1);

        Tag t2 = new Tag();
        t2.setTag("content2");
        t2.setNum(1l);
        insertTagList.add(t2);

        Tag t3 = new Tag();
        t3.setTag("content3");
        t3.setNum(1l);
        insertTagList.add(t3);

        tagMapper.insertList(insertTagList);

        List<String> selectTagList = new ArrayList<>();

        // List<Tag> resultList = tagMapper.selectByTagContents(selectTagList);
        // test to select by tagContent
        // assertEquals(2, resultList.size());
    }
}