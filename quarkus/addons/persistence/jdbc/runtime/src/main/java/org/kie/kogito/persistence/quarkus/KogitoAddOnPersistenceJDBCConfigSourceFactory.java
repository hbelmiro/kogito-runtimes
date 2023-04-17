/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.persistence.quarkus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;

import static org.kie.kogito.persistence.quarkus.KogitoAddOnPersistenceJDBCConfigSource.ORDINAL;

public class KogitoAddOnPersistenceJDBCConfigSourceFactory implements ConfigSourceFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(KogitoAddOnPersistenceJDBCConfigSourceFactory.class);

    private static final String FLYWAY_LOCATIONS = "quarkus.flyway.locations";
    private static final String DATASOURCE_DB_KIND = "quarkus.datasource.db-kind";
    private static final String LOCATION_PREFIX = "classpath:/db/";
    private static final String POSTGRESQL = "postgresql";
    private static final String ORACLE = "oracle";
    private static final String ANSI = "ansi";

    private static final String FLYWAY_ENABLED = "kogito.persistence.flyway.enabled";

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
        Map<String, String> configuration = new HashMap<>();

        String flyWayEnabledValue = context.getValue(FLYWAY_ENABLED).getValue();

        if (flyWayEnabledValue == null || "true".equals(flyWayEnabledValue)) {
            configuration.put(FLYWAY_ENABLED, "true");

            final String databaseName = context.getValue(DATASOURCE_DB_KIND).getValue();
            if (databaseName != null) {
                configuration.put(FLYWAY_LOCATIONS, LOCATION_PREFIX + getDBName(databaseName));
            } else {
                LOGGER.warn("Kogito Flyway must have the property \"quarkus.datasource.db-kind\" to be set to initialize process schema.");
            }
        } else {
            configuration.put(FLYWAY_ENABLED, "false");
        }
        return List.of(new KogitoAddOnPersistenceJDBCConfigSource(configuration));
    }

    @Override
    public OptionalInt getPriority() {
        return OptionalInt.of(ORDINAL);
    }

    private String getDBName(final String dbKind) {
        if (POSTGRESQL.equals(dbKind)) {
            return POSTGRESQL;
        } else if (ORACLE.equals(dbKind)) {
            return ORACLE;
        } else {
            return ANSI;
        }
    }
}
