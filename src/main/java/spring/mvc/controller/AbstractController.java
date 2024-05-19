package spring.mvc.controller;

import spring.mvc.view.JsonView;
import spring.mvc.view.ModelAndView;

public abstract class AbstractController {
    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
