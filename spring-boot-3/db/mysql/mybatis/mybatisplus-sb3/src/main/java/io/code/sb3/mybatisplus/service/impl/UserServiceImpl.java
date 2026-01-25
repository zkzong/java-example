package io.code.sb3.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.code.sb3.mybatisplus.domain.User;
import io.code.sb3.mybatisplus.mapper.UserMapper;
import io.code.sb3.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zongz
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2024-10-26 23:43:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




