package services;

import dto.Message;
import dto.security.LoginView;
import dto.security.RegisterView;
import enums.MessageType;
import enums.UserStatus;
import exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Http;
import play.mvc.Result;
import utils.Encryptor;
import views.html.security.login;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.redirect;

@Slf4j
@Transactional
@Singleton
public class LoginService {

    private final CaptchaService captchaService;
    private final FormFactory formFactory;

    @Inject
    public LoginService(CaptchaService captchaService, FormFactory formFactory) {
        this.captchaService = captchaService;
        this.formFactory = formFactory;
    }

    private void validate(LoginView form) {
        val messages = Http.Context.current().messages();

        User user = User.findByEmail(form.getEmail());
        if (user == null) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("login.incorrectEmail"))
                    .type(MessageType.WARNING.name())
                    .build());
            return;
        }
        if (!Encryptor.encryption(form.getPassword()).equals(user.getPassword())) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("register.incorrectPassword"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        if (!user.getType().name().equalsIgnoreCase(form.getType())) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("login.incorrectUserType"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        if (user.getStatus().equalsIgnoreCase(UserStatus.INACTIVE.name())) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("login.userNotActive"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        user.setLastLogin(new Timestamp((new Date()).getTime()));
        user.save();
    }

    public void logout() {
        Http.Context.current().session().clear();
    }

    public Result login(Form<LoginView> form) {
        val dto = form.get();
        val messages = Http.Context.current().messages();
        try {
            captchaService.verify();
            validate(dto);
            val user = User.findByEmail(dto.getEmail());
            Http.Context.current().session().put("username", user.getLogin());
            Http.Context.current().session().put("userType", user.getType().name());
            return redirect("/index");
        } catch (ValidationException e) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at(e.getMessage()))
                    .type(MessageType.WARNING.name())
                    .build());
            return badRequest(login.render(form, formFactory.form(RegisterView.class)));
        } catch (IOException e) {
            log.error("Problem with captcha. ", e);
            dto.getMessages().add(Message.builder()
                    .text(messages.at("error"))
                    .type(MessageType.WARNING.name())
                    .build());
            return badRequest(login.render(form, formFactory.form(RegisterView.class)));
        }
    }
}