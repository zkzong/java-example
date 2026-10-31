package io.code.sb3.dynamicdatasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import io.code.sb3.dynamicdatasource.domain.User;
import io.code.sb3.dynamicdatasource.mapper.UserMapper;
import io.code.sb3.dynamicdatasource.service.SlaveUserService;
import org.springframework.stereotype.Service;

/**
 * @author zongz
 * @description 针对表【t_user】的数据库操作Service实现，通过 @DS 切换到 slave 数据源
 * @createDate 2024-10-26 23:43:24
 */
@Service
@DS("slave")
public class SlaveUserServiceImpl extends ServiceImpl<UserMapper, User>
        implements SlaveUserService {

}
