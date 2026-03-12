package co.com.petstore.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue    = "co.com.petstore.stepdefinitions",
    plugin  = {"pretty"},
    tags    = "@regresion"
)
public class UserCRUDRunner {
}
