package traffic_id.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewOnlyConfiguration implements WebMvcConfigurer {

  // @Override
  // public void addViewControllers(@SuppressWarnings("null") ViewControllerRegistry registry) {
  //   registry.addViewController("/login.html").setViewName("login");
  // }
}