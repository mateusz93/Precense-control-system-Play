package services;

import dto.Message;
import dto.security.RegisterView;
import enums.MessageType;
import enums.Role;
import enums.UserStatus;
import enums.UserType;
import lombok.val;
import models.Token;
import models.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Http;
import play.mvc.Result;
import utils.Encryptor;
import utils.PasswordValidator;
import views.html.security.register;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static play.mvc.Results.ok;

@Transactional
@Singleton
public class RegisterService {

    private final FormFactory formFactory;
    private final EmailService emailService;
    private static final String APPLICATION_ADDRESS = "http://localhost:9000";

    @Inject
    public RegisterService(FormFactory formFactory, EmailService emailService) {
        this.formFactory = formFactory;
        this.emailService = emailService;
    }

    private User activateUser(User user) {
        user.setStatus(UserStatus.ACTIVE.name());
        user.save();
        return user;
    }

    private String generateToken(User user) {
        String tokenAsString = UUID.randomUUID().toString();
        Token token = new Token();
        token.setUser(user);
        token.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
        token.setToken(tokenAsString);
        token.save();
        return tokenAsString;
    }

    private String generateActivationLink(String token) {
        return APPLICATION_ADDRESS + "/register/registrationConfirm/" + token;
    }

    private User accept(RegisterView form) {
        validate(form);
        String username = generateUsername(form.getFirstName(), form.getLastName());
        User user = createUser(form, username);
        user.save();
        return user;
    }

    private User createUser(RegisterView form, String username) {
        return User.builder()
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .type(UserType.STUDENT.toString().equalsIgnoreCase(form.getType()) ? Role.STUDENT.name() : Role.TEACHER.name())
                .login(username)
                .password(Encryptor.encryption(form.getPassword()))
                .status(UserStatus.INACTIVE.name())
                .build();
    }

    private void validate(RegisterView dto) {
        val messages = Http.Context.current().messages();
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.differentPasswords"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        if (!PasswordValidator.validate(dto.getPassword())) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.incorrectPassword"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        User user = User.findByEmail(dto.getEmail());
        if (user != null) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.emailUsed"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
    }

    private String generateUsername(String firstName, String lastName) {
        String username = firstName + lastName;

        for (int i = 0; i < 1000; ++i) {
            User user = User.findByLogin(username);
            if (user == null) {
                break;
            }
            username = username + i;
        }
        return username;
    }

    public void registerUser(Form<RegisterView> form) {
        val dto = form.get();
        val messages = Http.Context.current().messages();
        User user = accept(dto);
        String token = generateToken(user);
        String activationLink = generateActivationLink(token);
        emailService.sendActivationLink(user, activationLink);

        if (CollectionUtils.isEmpty(dto.getMessages())) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.userCreated"))
                    .type(MessageType.INFO.name())
                    .build());
        }
        dto.setLastName(StringUtils.EMPTY);
        dto.setFirstName(StringUtils.EMPTY);
        dto.setEmail(StringUtils.EMPTY);
        dto.setPassword(StringUtils.EMPTY);
        dto.setConfirmPassword(StringUtils.EMPTY);
    }

    public Result confirmRegistration(String tokenAsString) {
        Form<RegisterView> wrappedDto = formFactory.form(RegisterView.class).fill(new RegisterView());
        Token token = Token.findByToken(tokenAsString);
        validateToken(token, wrappedDto.get());

        return ok(register.render(wrappedDto, null));
    }

    private void validateToken(Token token, RegisterView dto) {
        val messages = Http.Context.current().messages();
        if (token == null) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.token.incorrect"))
                    .type(MessageType.WARNING.name())
                    .build());
        } else if (token.getExpiryDate().getTime() < Date.valueOf(LocalDate.now()).getTime()) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.token.expiry"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        if (CollectionUtils.isEmpty(dto.getMessages())) {
            activateUser(token.getUser());
            token.delete();
            dto.getMessages().add(Message.builder()
                    .text(messages.at("view.register.confirmed"))
                    .type(MessageType.INFO.name())
                    .build());
        }
    }
}
