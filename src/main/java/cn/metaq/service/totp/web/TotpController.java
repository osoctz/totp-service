package cn.metaq.service.totp.web;

import cn.metaq.common.web.dto.Result;
import cn.metaq.kit.totp.auth.TotpAuthenticator;
import cn.metaq.kit.totp.auth.TotpAuthenticatorConfig.TotpAuthenticatorConfigBuilder;
import cn.metaq.kit.totp.auth.TotpAuthenticatorKey;
import cn.metaq.kit.totp.auth.TotpAuthenticatorQRGenerator;
import cn.metaq.service.totp.dto.OtpAuthDTO;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TotpController {

  @GetMapping("/bind")
  public Result bind(@RequestParam("username") String username) {

    TotpAuthenticator authenticator = new TotpAuthenticator();
    TotpAuthenticatorKey key = authenticator.createCredentials(username);
    //密钥串
    String secret = key.getKey();
    //List<Integer> scratchCodes = key.getScratchCodes();
    //二维码地址
    String otpAuthURL = TotpAuthenticatorQRGenerator.getOtpAuthURL("metaq.cn", username, key);

    OtpAuthDTO otpAuthDTO = new OtpAuthDTO();
    otpAuthDTO.setOtpAuthURL(otpAuthURL);
    otpAuthDTO.setSecret(secret);
    otpAuthDTO.setUsername(username);

    return Result.ok(otpAuthDTO);
  }

  @GetMapping("/check")
  public Result check(@RequestParam String username, @RequestParam Integer codeInput) {

    TotpAuthenticatorConfigBuilder gacb =
        new TotpAuthenticatorConfigBuilder()
            .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30))
            .setWindowSize(5)
            .setCodeDigits(6);
    TotpAuthenticator ga = new TotpAuthenticator(gacb.build());

    boolean isCodeValid = ga.authorizeUser(username, codeInput, new Date().getTime());

    return Result.ok(isCodeValid);
  }
}
