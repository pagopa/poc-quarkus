package it.gov.pagopa.Config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "azurite")
@StaticInitSafe
public interface Configs {
    String connection();

    String table();
}
