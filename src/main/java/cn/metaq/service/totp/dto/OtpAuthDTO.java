package cn.metaq.service.totp.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OtpAuthDTO implements Serializable {

  private String otpAuthURL;
  private String secret;
  private String username;
}
