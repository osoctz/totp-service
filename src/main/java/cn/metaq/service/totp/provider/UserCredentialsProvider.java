package cn.metaq.service.totp.provider;

import cn.metaq.kit.totp.auth.ICredentialRepository;
import cn.metaq.service.totp.dao.UserCredentialsDao;
import cn.metaq.service.totp.entity.UserCredentials;
import cn.metaq.service.totp.web.App;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.WebUtils;

public class UserCredentialsProvider implements ICredentialRepository {

  @Override
  public String getSecretKey(String username) {

//    UserCredentials userCredentials = new UserCredentials();
//    userCredentials.setUsername(username);
//    ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("username", GenericPropertyMatchers.exact());
//
//    Example<UserCredentials> ex = Example.of(userCredentials, matcher);

    JdbcTemplate jt = App.getBean(JdbcTemplate.class);
    UserCredentials userCredentials = jt.queryForObject("select * from USER_CREDENTIALS where username =?",
        new Object[]{username}, (rs, i) -> {
          UserCredentials userCredentials1 = new UserCredentials();
          userCredentials1.setUsername(username);
          userCredentials1.setSecretKey(rs.getString("secret_key"));
          userCredentials1.setValidationCode(rs.getInt("validation_code"));
          userCredentials1.setScratchCodes(rs.getString("scratch_codes"));
          return userCredentials1;
        });
//    Optional<UserCredentials> re = App.getBean(UserCredentialsDao.class).findOne(ex);

    if (userCredentials != null) {
      return userCredentials.getSecretKey();
    }
    return "";
  }

  @Override
  public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {

    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setUsername(username);
    userCredentials.setSecretKey(secretKey);
    userCredentials.setValidationCode(validationCode);
    userCredentials.setScratchCodes(StringUtils.collectionToDelimitedString(scratchCodes, ","));

    App.getBean(UserCredentialsDao.class).save(userCredentials);
  }
}
