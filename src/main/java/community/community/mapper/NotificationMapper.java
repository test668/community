package community.community.mapper;

import community.community.model.Notification;
import community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("Insert into notification (id,notifier,receiver,outer_id,type,gmt_create,status,notifier_name,outer_title) values (#{id},#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("Select count(1) from notification where receiver=#{userId}")
    Integer countByUserId(@Param(value = "userId") Long userId);

    @Select("Select * from notification where receiver=#{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> ListByUserId(@Param(value = "userId") Long userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select * from notification where id=#{id}")
    Notification selectById(@Param(value = "id")Integer id);

    @Update("update notification set status=#{status} where id=#{id}")
    void updateById(Notification notification);

    @Select("Select count(1) from notification where receiver=#{id} and status=#{status}")
    Integer countByUserIdAndStatus(@Param(value = "id")long id,@Param(value = "status") int status);
}
