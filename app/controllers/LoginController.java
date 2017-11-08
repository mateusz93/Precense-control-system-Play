package controllers;

import dto.security.LoginView;
import dto.security.RegisterView;
import lombok.val;
import play.data.FormFactory;
import play.mvc.Result;
import services.LoginService;
import views.html.security.login;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginController extends BaseController {

    private final LoginService service;
    private final FormFactory formFactory;

    @Inject
    public LoginController(LoginService service, FormFactory formFactory) {
        this.service = service;
        this.formFactory = formFactory;
    }

    public Result logIn() {
        return ok(login.render(formFactory.form(LoginView.class), formFactory.form(RegisterView.class)));
    }

    public Result loginSubmit() {
        val registerView = formFactory.form(RegisterView.class).bindFromRequest();
        val loginView = formFactory.form(LoginView.class).bindFromRequest();

        if (loginView.hasErrors()){
            wrapErrors(loginView);
            return badRequest(login.render(loginView, registerView));
        }
        else {
            return service.login(loginView.bindFromRequest());
        }
    }

    public Result logout() {
        service.logout();
        return redirect("/");
    }
}