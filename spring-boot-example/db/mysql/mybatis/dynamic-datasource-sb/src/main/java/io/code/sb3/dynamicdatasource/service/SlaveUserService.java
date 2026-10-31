package io.code.sb3.dynamicdatasource.service;

import com.baomidou.mybatisplus.spring.service.IService;
import io.code.sb3.dynamicdatasource.domain.User;

/**
 * @author zongz
 * @description 针对表【t_user】的数据库操作Service，使用 slave 数据源
 * @createDate 2024-10-26 23:43:24
 */
public interface SlaveUserService extends IService<User> {

}
