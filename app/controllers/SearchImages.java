package controllers;

import play.*;
import play.mvc.*;
import play.libs.Json;

import views.html.*;

import play.data.Form;
import play.data.DynamicForm;

import java.util.ArrayList;

import services.VarnishSearch;

public class SearchImages extends Controller {

    public static Result search() {

    	DynamicForm form = Form.form().bindFromRequest();

    	String stock_ref = form.get("stock_ref").trim();
    	String registration = form.get("registration").trim();

    	VarnishSearch search = new VarnishSearch(stock_ref, registration);
    	ArrayList<String> results = search.createUrlList();

       	return ok(Json.toJson(results));
    }

}
