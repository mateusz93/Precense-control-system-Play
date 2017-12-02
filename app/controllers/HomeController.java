package controllers;

import play.mvc.Result;
import views.html.index;

import javax.inject.Singleton;

@Singleton
public class HomeController extends BaseController {

    public Result index() {
        return ok(index.render());
    }

    public Result ENLanguage() {
        ctx().changeLang("en");
        return redirect("/");
    }

    public Result PLLanguage() {
        ctx().changeLang("pl");
        return redirect("/");
    }
}