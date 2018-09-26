package pe.com.entel.sincrespago.main;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import pe.com.entel.sincrespago.service.SincResPagoService;

/**
 * Created by Erika Rumiche on 21/09/2018.
 */
@Component
public class SincResPagoMain {

    @Autowired
    private SincResPagoService sincResPagoService;

    static Logger logger = Logger.getLogger(SincResPagoMain.class);

    public static void main(String[] args) {

        logger.info("Inicio SincResPagoMain");
        ClassPathXmlApplicationContext ctx = null;
        try {
            ctx = new ClassPathXmlApplicationContext("spring-context.xml");
            ctx.registerShutdownHook();

            SincResPagoMain main = ctx.getBean(SincResPagoMain.class);
            main.inicio();

        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (ctx != null) {
                ctx.close();
            }
        }
        logger.info("Fin SincResPagoMain");
    }

    public void inicio() throws Exception {
        sincResPagoService.sincronizarResPago();

    }

}
