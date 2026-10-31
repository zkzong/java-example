package io.code.sb3.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import io.code.sb3.mybatisplus.domain.User;
import io.code.sb3.mybatisplus.mapper.UserMapper;
import io.code.sb3.mybatisplus.service.User2Service;
import org.springframework.stereotype.Service;

/**
 * @author zongz
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2024-10-26 23:43:24
 */
@Service
@DS("slave")
public class User2ServiceImpl extends ServiceImpl<UserMapper, User>
        implements User2Service {

}




