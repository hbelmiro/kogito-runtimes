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
package org.kie.kogito.serverless.workflow.operationid;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import org.kie.kogito.serverless.workflow.io.URIContentLoaderFactory;
import org.kie.kogito.serverless.workflow.parser.ParserContext;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.functions.FunctionDefinition;

public class FileNameWorkflowOperationIdFactory extends AbstractWorkflowOperationIdFactory {

    public static final String FILE_PROP_VALUE = "FILE_NAME";

    @Override
    public String getFileName(Workflow workflow, FunctionDefinition function, Optional<ParserContext> context, URI uri, String operation, String service) {
        return URIContentLoaderFactory.getFileName(uri);
    }

    @Override
    public Set<String> propertyValues() {
        return Set.of(FILE_PROP_VALUE);
    }
}
