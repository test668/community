package community.community.service;

import community.community.dto.NotificationDto;
import community.community.dto.PaginationDto;
import community.community.enums.NotificationStatusEnum;
import community.community.enums.NotificationTypeEnum;
import community.community.exception.CustomizeException;
import community.community.mapper.NotificationMapper;
import community.community.mapper.UserMapper;
import community.community.model.Notification;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;



    public PaginationDto List(Integer userId, Integer page, Integer size) {
        PaginationDto<NotificationDto> paginationDto = new PaginationDto<>();
        Integer totalCount =notificationMapper.countByUserId((long)userId);
        if(totalCount==0){
            return new PaginationDto();
        }else {
            Integer totalPage;
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            paginationDto.setPagination(totalPage, page);

            Integer offset = size * (page - 1);
            List<Notification> notifications = notificationMapper.ListByUserId((long)userId, offset, size);
            if (notifications.size()==0){
                return paginationDto;
            }
            List<NotificationDto> notificationDtoList = new ArrayList<>();
            for (Notification notification : notifications) {
                NotificationDto notificationDto=new NotificationDto();
                BeanUtils.copyProperties(notification,notificationDto);
                notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

                notificationDtoList.add(notificationDto);
            }
            paginationDto.setData(notificationDtoList);
            return paginationDto;
        }

    }

    public Integer unread(Integer id) {

        return notificationMapper.countByUserIdAndStatus((long)id,NotificationStatusEnum.UNREAD.getStatus());
    }

    public NotificationDto read(Integer id, User user) {
       Notification notification= notificationMapper.selectById(id);
       if (notification==null){
           throw new CustomizeException("无消息");
       }
       if (notification.getReceiver()!=(long)user.getId()){
           throw new CustomizeException("错误");
       }
       notification.setStatus(NotificationStatusEnum.READ.getStatus());
       notificationMapper.updateById(notification);
        NotificationDto notificationDto=new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}
