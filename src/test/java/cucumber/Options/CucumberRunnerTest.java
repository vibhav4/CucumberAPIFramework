package cucumber.Options;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {"stepDefinations"},
//        tags = "@deleteplace"
        plugin = {"pretty"
//                "json:target/MyReports/report.json",
//                "junit:target/MyReports/report.xml"
        }
//        dryRun = true

)
public class CucumberRunnerTest {
}
