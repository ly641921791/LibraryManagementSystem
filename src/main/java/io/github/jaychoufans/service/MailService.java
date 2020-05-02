package io.github.jaychoufans.service;

public interface MailService {

	void sendSimpleMail(String to, String subject, String content);

}
