import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;

public class MirrorTests {
    @Test
    void mirrorTest() {

        // I also added to the dependencies 'com.github.valfirst.browserup-proxy:browserup-proxy-core:2.2.15'
        // If it is not present, as of 22.11.2023 the code can't infer Java type of proxy to use its methodsы

        Configuration.proxyEnabled = true;
        Configuration.holdBrowserOpen = true;

        open();

        WebDriverRunner.getSelenideProxy().getProxy().addHeader("X_REMOTE_USER",
                "JessyJames");

        getSelenideProxy().addRequestFilter("proxy-usages.request", (request, contents, messageInfo) -> {
            request.headers().add("X_REMOTE_USER", "JessyJames");
            return null;
        } );

        open("https://apichallenges.herokuapp.com/mirror/request?statusCode=100");

        $("body").shouldHave(Condition.text("X_remote_user"));

    }
}
