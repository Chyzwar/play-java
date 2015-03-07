import org.junit.Test;
import play.mvc.Result;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class IndexRouteTest {

    @Test
    public void testIndexRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }

}