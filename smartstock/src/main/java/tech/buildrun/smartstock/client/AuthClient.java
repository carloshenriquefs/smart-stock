package tech.buildrun.smartstock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.buildrun.smartstock.client.dto.AuthRequest;
import tech.buildrun.smartstock.client.dto.AuthResponse;

@FeignClient(name = "AuthClient", url = "${api.auth-url}")
public interface AuthClient {

    @PostMapping(path = "/api/token")
    ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request);
}
