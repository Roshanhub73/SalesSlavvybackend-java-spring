package com.kodnest.project.app.USER;

public interface AuthServiceContract {
public user authenticate(String username, String password);
public String generateToken(user user);
public String generateNewToken(user user);
public void saveToken(user user, String token);
}
