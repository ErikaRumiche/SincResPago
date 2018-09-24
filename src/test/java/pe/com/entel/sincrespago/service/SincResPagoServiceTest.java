package pe.com.entel.sincrespago.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class SincResPagoServiceTest {

    Logger logger = Logger.getLogger(SincResPagoServiceTest.class);

    @Autowired
    private SincResPagoService sincResPagoService;

    @Test
    public void sincronizarInfoTest() throws Exception {
        sincResPagoService.sincronizarInfo();

    }
}
