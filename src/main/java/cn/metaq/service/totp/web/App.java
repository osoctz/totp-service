package cn.metaq.service.totp.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class App implements ApplicationContextAware {

  public static ApplicationContext ac;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ac = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return ac;
  }

  public static Object getBean(String name) {
    return ac.getBean(name);
  }

  public static <T> T getBean(Class<T> clazz) {
    return ac.getBean(clazz);
  }

  public static <T> T getBean(String name, Class<T> clazz) {
    return ac.getBean(name, clazz);
  }

}
