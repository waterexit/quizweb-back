package quizweb.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import quizweb.domain.repository.entity.Tag;

@Mapper
public interface TagMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    int insert(Tag record);

    int insertList(List<Tag> record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    int insertSelective(Tag record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    Tag selectByPrimaryKey(Long id);

    List<Tag> selectByTagContents(List<String> tagContent);

    List<Tag> selectByPrefix(String content);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    int updateByPrimaryKeySelective(Tag record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table tag
     *
     * @mbg.generated Sat Oct 16 02:39:48 UTC 2021
     */
    int updateByPrimaryKey(Tag record);

    int updateNum(List<Tag> updateList);

}