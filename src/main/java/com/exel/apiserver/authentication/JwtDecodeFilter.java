package com.exel.apiserver.authentication;

import com.exel.apiserver.exceptions.ApiInternalException;
import io.fusionauth.jwt.JWTUtils;
import io.fusionauth.jwt.domain.JWT;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Setter
@AllArgsConstructor
public class JwtDecodeFilter extends OncePerRequestFilter {
	@SneakyThrows
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
	                                HttpServletResponse httpServletResponse,
	                                FilterChain filterChain) {
		filter(httpServletRequest, httpServletResponse, filterChain);
	}

	private void filter(HttpServletRequest httpServletRequest,
	                    HttpServletResponse httpServletResponse,
	                    FilterChain filterChain) throws IOException, ServletException {
		String authenticationHeader = httpServletRequest.getHeader("Authorization");
		if(!StringUtils.hasText(authenticationHeader)) {
			throw ApiInternalException
					.builder()
					.status(HttpStatus.UNAUTHORIZED)
					.message("Bareer Authorization header is required")
					.build();
		}
		String token = authenticationHeader.replace("Bearer","").trim();
		JWT jwt = JWTUtils.decodePayload(token);
		String userId = jwt.getString("sub");
		httpServletRequest.setAttribute("userId", userId);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
