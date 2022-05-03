import models.Hero;
import models.Squad;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.lang.reflect.Array.get;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        ProcessBuilder process = new ProcessBuilder();
        int port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);
        //get: retrieve user session in homepage first
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int totalHeroes = Hero.getHeroRegistry().size();
            int totalSquads = Squad.getAllSquads().size();
            int squadlessHeroes = 0;
            int squadfullHeroes = 0;
            for (Hero hero : Hero.getHeroRegistry()) {
                if (hero.getSquadAlliance().equals("")) {
                    squadlessHeroes += 1;
                } else {
                    squadfullHeroes += 1;
                }
            }
            model.put("totalHeroes", totalHeroes);
            model.put("totalSquads", totalSquads);
            model.put("squadlessHeroes", squadlessHeroes);
            model.put("squadfullHeroes", squadfullHeroes);
            model.put("uniqueId", request.session().attribute("uniqueId"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
        //post: store user session - redirect back home

        post("/success", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String uniqueId = request.queryParams("uniqueId");
            request.session().attribute("uniqueId", uniqueId);
            model.put("uniqueId", uniqueId);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
        //get: create hero page
        get("/heroes/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("uniqueId", request.session().attribute("uniqueId"));
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());
        //post: submit a new hero - redirect to success page
        post("/heroes/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            int age = Integer.parseInt(request.queryParams("age"));
            String power = request.queryParams("power");
            String weakness = request.queryParams("weakness");
            Hero newHero = new Hero(name, age, power, weakness);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

    }

}
