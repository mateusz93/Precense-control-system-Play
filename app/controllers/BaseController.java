package controllers;

import dto.BaseView;
import dto.Message;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;

import javax.inject.Singleton;

@Singleton
abstract class BaseController extends Controller {

    Form wrapErrors(Form form) {
        BaseView view = (BaseView) form.value().get();
        for(Message message : view.getMessages()) {
            form = form.withError(new ValidationError(message.getType(), message.getText()));
        }
        return form;
    }
}
