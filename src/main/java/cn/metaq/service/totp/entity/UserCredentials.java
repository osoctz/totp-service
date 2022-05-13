package cn.metaq.service.totp.entity;

import cn.metaq.common.core.IEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "USER_CREDENTIALS")
@Setter
@Getter
public class UserCredentials implements IEntity<Long> {

  /**
   * ID
   */
  @Id
  @Column(value = "id" )
  private Long id;

  private String username;
  private String secretKey;
  private Integer validationCode;
  private String scratchCodes;
}
