package pe.com.entel.sincrespago.main;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Erika Rumiche on 21/09/2018.
 */
@Component
public class SincResPagoMain {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    //private ModeRepository modeRepository;

    static Logger logger = Logger.getLogger(SincResPagoMain.class);


}
