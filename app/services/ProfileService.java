package services;

import dto.Message;
import dto.user.ProfileGeneralView;
import dto.user.ProfilePasswordView;
import enums.MessageType;
import lombok.val;
import models.StudentGroup;
import models.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Http;
import utils.Encryptor;
import utils.PasswordValidator;

import javax.inject.Inject;
import javax.inject.Singleton;

import static play.mvc.Results.ok;

@Transactional
@Singleton
public class ProfileService {

    private final FormFactory formFactory;

    @Inject
    public ProfileService(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Form<ProfileGeneralView> update(ProfileGeneralView form) {
        val messages = Http.Context.current().messages();

        if (!areFieldsNotEmpty(form)) {
            form.getMessages().add(Message.builder()
                    .text(messages.at("emptyField"))
                    .type(MessageType.WARNING.name())
                    .build());
        }

        if (CollectionUtils.isEmpty(form.getMessages())) {
            saveUser(form);
            form.getMessages().add(Message.builder()
                    .text(messages.at("profile.updated"))
                    .type(MessageType.INFO.name())
                    .build());
        }
        return formFactory.form(ProfileGeneralView.class).fill(form);
    }

    private User saveUser(ProfileGeneralView form) {
        User user = User.findByEmail(form.getEmail());
        user.setStudentGroup(StudentGroup.findByName(form.getGroup()));
        user.setPhone(form.getPhone());
        user.setCity(form.getCity());
        user.setStreet(form.getStreet());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.save();
        return user;
    }

    private boolean areFieldsNotEmpty(ProfileGeneralView form) {
        return StringUtils.isNotBlank(form.getFirstName()) && StringUtils.isNotBlank(form.getLastName());
    }

    private boolean arePasswordFieldsNotEmpty(ProfilePasswordView form) {
        return StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getNewPassword())
                && StringUtils.isNotBlank(form.getAgainNewPassword());
    }

    public Form<ProfilePasswordView> updatePassword(Form<ProfilePasswordView> passwordView) {
        updatePassword(passwordView.get());
        return passwordView;
    }

    private void updatePassword(ProfilePasswordView dto) {
        val messages = Http.Context.current().messages();
        val username = Http.Context.current().session().get("username");
        val user = User.findByLogin(username);

        if (!arePasswordFieldsNotEmpty(dto)) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("emptyField"))
                    .type(MessageType.WARNING.name())
                    .build());
        }

        if (!dto.getNewPassword().equals(dto.getAgainNewPassword())) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.differentPasswords"))
                    .type(MessageType.WARNING.name())
                    .build());
        }

        if (!PasswordValidator.validate(dto.getNewPassword())) {
            dto.getMessages().add(Message.builder()
                    .text(messages.at("register.incorrectPassword"))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        user.setPassword(Encryptor.encryption(dto.getNewPassword()));
        user.save();
    }

    public Form<ProfileGeneralView> prepareGeneralProfileView() {
        val username = Http.Context.current().session().get("username");
        val user = User.findByLogin(username);
        return prepareGeneralData(user);
    }

    private Form<ProfileGeneralView> prepareGeneralData(User user) {
        val dto = ProfileGeneralView.builder()
                .city(user.getCity())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .group(user.getStudentGroup() != null ? user.getStudentGroup().getName() : "")
                .phone(user.getPhone())
                .street(user.getStreet())
                .type(user.getType())
                .ID(user.getId())
                .build();
        return formFactory.form(ProfileGeneralView.class).fill(dto);
    }

}