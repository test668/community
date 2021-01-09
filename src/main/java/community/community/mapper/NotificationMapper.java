package community.community.mapper;

import community.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("Insert into notification (id,notifier,receiver,outer_id,type,gmt_create,status,notifier_name,outer_title,flag_id) values (#{id},#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle},#{flagId})")
    void insert(Notification notification);

    @Select("Select count(1) from notification where receiver=#{userId} and status!=2")
    Integer countByUserId(@Param(value = "userId") Long userId);

    @Select("Select * from notification where receiver=#{userId} and status!=2 order by gmt_create desc limit #{offset},#{size}")
    List<Notification> ListByUserId(@Param(value = "userId") Long userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("Select * from notification where id=#{id} and status!=2")
    Notification selectById(@Param(value = "id") Integer id);

    @Update("update notification set status=#{status} where id=#{id}")
    void updateById(Notification notification);

    @Select("Select count(1) from notification where receiver=#{id} and status=#{status}")
    Integer countByUserIdAndStatus(@Param(value = "id") long id, @Param(value = "status") int status);

    @Update("update notification set status=#{status} where receiver=#{receiver} and notifier=#{notifier} and flag_id=#{flagId} and type=#{type}")
    void deleteNotification(Notification notification);

    @Update("update notification set status=#{status} where receiver=#{receiver} and status!=2")
    void deleteAllNotification(Notification notification);
}
