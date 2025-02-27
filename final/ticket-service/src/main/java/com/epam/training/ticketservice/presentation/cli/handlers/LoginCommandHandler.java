package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.service.LoginService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class LoginCommandHandler {

    private LoginService loginService;

    public LoginCommandHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Login as Admin User", key = "sign in privileged")
    public String loginWithPrivilegedAccount(String username, String password) {
        Optional<UserAccount> privilegedAccount = loginService.signInWithPrivileged(username, password);

        return privilegedAccount.isPresent() ? "Login success" : "Login failed due to incorrect credentials";
    }

    @ShellMethod(value = "Login as Normal User", key = "sign in")
    public String loginWithNormalAccount(String username, String password) {
        Optional<UserAccount> userAccount = loginService.signInWithUser(username, password);

        return userAccount.isPresent() ? "Login success" : "Login failed due to incorrect credentials";
    }

    @ShellMethod(value = "Sign out with User", key = "sign out")
    public String signOut() {
        loginService.signOut();
        return "Successfully signed out!";
    }

    @ShellMethod(value = "Describe logged in User Account:", key = "describe account")
    public String describeAccount() {
        Optional<UserAccount> userAccount = loginService.describeLoggedInAccount();

        if (!userAccount.isPresent()) {
            return "You are not signed in";
        }

        if (userAccount.get().isAdminUser()) {
            return "Signed in with privileged account '" + userAccount.get().getUsername() + "'";
        }

        return "Signed in with account '" + userAccount.get().getUsername() + "'";
    }
}
