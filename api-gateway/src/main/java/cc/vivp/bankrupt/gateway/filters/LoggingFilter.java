package cc.vivp.bankrupt.gateway.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LoggingFilter extends ZuulFilter {

  private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {

    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
    logger.info("Request to URI {} with response status {}", request.getRequestURL(),
        response.getStatus());

    return null;
  }

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

}
