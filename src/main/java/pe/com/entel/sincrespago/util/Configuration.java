package pe.com.entel.sincrespago.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Erika Rumiche on 24/09/2018.
 */
@Component
public class Configuration {

    @Value("${websales.schema}")
    private String websalesSchema;

    @Value("${sincresppago.package}")
    private String sincresppagoPackage;

    @Value("${resp.ovepcerrada.sp}")
    private String respOvepcerradaSp;

    @Value("${apibscs.schema}")
    private String apibscsSchema;

    @Value("${custcodebysim.sp}")
    private String custcodebysimSp;

    public String getWebsalesSchema() {
        return websalesSchema;
    }

    public String getSincresppagoPackage() {
        return sincresppagoPackage;
    }

    public String getRespOvepcerradaSp() {
        return respOvepcerradaSp;
    }

    public String getApibscsSchema() {
        return apibscsSchema;
    }

    public String getCustcodebysimSp() {
        return custcodebysimSp;
    }
}
