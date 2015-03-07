package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import play.data.Form;
import play.data.DynamicForm;

import services.VarnishSearch;

public class SearchImages extends Controller {

    public static Result search() {

    	Form<VarnishSearch> searchForm = Form.form(VarnishSearch.class);
    	VarnishSearch search = searchForm.bindFromRequest().get();

    	Logger.debug(search.toString());

       	return ok();
    }

}
