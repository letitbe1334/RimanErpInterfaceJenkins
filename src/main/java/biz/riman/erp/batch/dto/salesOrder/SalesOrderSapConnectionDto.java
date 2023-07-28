package biz.riman.erp.batch.dto.salesOrder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sales-order")
public class SalesOrderSapConnectionDto {
	private String endpointHost;
	private String uri;
    private String userName;
    private String password;
    
    public String getEndpointHost() {
        return endpointHost;
    }
    public void setEndpointHost(String endpointHost) {
        this.endpointHost = endpointHost;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
