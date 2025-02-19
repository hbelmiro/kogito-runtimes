/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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
package org.kie.kogito.event.avro;

import java.io.IOException;
import java.util.function.Function;

import org.kie.kogito.event.CloudEventMarshaller;

import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;

public class AvroCloudEventMarshaller implements CloudEventMarshaller<byte[]> {

    private final AvroIO avroUtils;

    public AvroCloudEventMarshaller(AvroIO avroUtils) {
        this.avroUtils = avroUtils;
    }

    @Override
    public byte[] marshall(CloudEvent event) throws IOException {
        return avroUtils.writeCloudEvent(event);
    }

    @Override
    public <T> Function<T, CloudEventData> cloudEventDataFactory() {
        return new AvroCloudEventDataFactory<>(avroUtils);
    }
}
