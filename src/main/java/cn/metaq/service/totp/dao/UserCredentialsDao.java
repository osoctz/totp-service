package cn.metaq.service.totp.dao;

import cn.metaq.data.jdbc.BaseDao;
import cn.metaq.service.totp.entity.UserCredentials;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsDao extends BaseDao<UserCredentials,Long> {

}
