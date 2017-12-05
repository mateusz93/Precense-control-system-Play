package controllers;

import dto.BaseView;
import dto.Message;
import enums.MessageType;
import lombok.val;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Http;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public abstract class BaseController extends Controller {

    public static final String USERNAME = "username";

    public static Form wrapCustomErrors(Form form) {
        BaseView view = (BaseView) form.value().get();
        for(Message message : view.getMessages()) {
            form = form.withError(new ValidationError(message.getType(), message.getText()));
        }
        return form;
    }

    void wrapErrors(Form form) {
        BaseView view = (BaseView) form.value().get();
        val messages = Http.Context.current().messages();
        List<Message> validationMessages = new ArrayList<>();
        for(ValidationError error : (List<ValidationError>) form.allErrors()) {
            validationMessages.add(Message.builder()
                    .text(messages.at(error.messages().get(0), error.key()))
                    .type(MessageType.WARNING.name())
                    .build());
        }
        view.setMessages(validationMessages);
        wrapCustomErrors(form);
    }
}
