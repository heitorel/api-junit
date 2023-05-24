import br.com.heitor.apijunit.ApiJunitApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ApiJunitApplicationTests {

    @Test
    public final void testMain() {
        ApiJunitApplication.main(null);
    }
}
