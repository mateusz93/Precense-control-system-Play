package services;

import controllers.BaseController;
import dto.Message;
import dto.security.LoginView;
import dto.security.RegisterView;
import enums.MessageType;
import enums.UserStatus;
import exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import models.StudentGroup;
import models.User;
import org.apache.commons.collections4.CollectionUtils;
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
        if (!user.getType().equalsIgnoreCase(form.getType())) {
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
        if (CollectionUtils.isEmpty(form.getMessages())) {
            user.setLastLogin(new Timestamp((new Date()).getTime()));
            user.save();
        }
    }

    public void registerValidation(RegisterView form){
        val messages = Http.Context.current().messages();

        User user = User.findByEmail(form.getEmail());
        if (user != null) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("register.incorrectEmail"))
                    .type(MessageType.WARNING.name())
                    .build());
            return;
        }
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
            if (CollectionUtils.isEmpty(dto.getMessages())) {
                val user = User.findByEmail(dto.getEmail());
                Http.Context.current().session().put("username", user.getLogin());
                Http.Context.current().session().put("userType", user.getType());
                return redirect("/");
            }
            return badRequest(login.render(BaseController.wrapCustomErrors(form), formFactory.form(RegisterView.class), StudentGroup.findAll()));
        } catch (ValidationException e) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at(e.getMessage()))
                    .type(MessageType.WARNING.name())
                    .build());
            return badRequest(login.render(BaseController.wrapCustomErrors(form), formFactory.form(RegisterView.class), StudentGroup.findAll()));
        } catch (IOException e) {
            log.error("Problem with captcha. ", e);
            dto.getMessages().add(Message.builder()
                    .text(messages.at("error"))
                    .type(MessageType.WARNING.name())
                    .build());
            return badRequest(login.render(BaseController.wrapCustomErrors(form), formFactory.form(RegisterView.class), StudentGroup.findAll()));
        }
    }
}