package io.github.jaychoufans.service;

public interface VaptchaCheckService {

	boolean vaptchaCheck(String token, String ip) throws Exception;

}
