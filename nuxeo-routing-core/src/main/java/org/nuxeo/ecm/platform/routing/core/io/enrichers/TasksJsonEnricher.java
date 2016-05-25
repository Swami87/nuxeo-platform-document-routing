/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     <a href="mailto:grenard@nuxeo.com">Guillaume Renard</a>
 */

package org.nuxeo.ecm.platform.routing.core.io.enrichers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.io.marshallers.json.OutputStreamWithJsonWriter;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.Writer;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext.SessionWrapper;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;
import org.nuxeo.ecm.platform.routing.api.DocumentRoutingService;
import org.nuxeo.ecm.platform.task.Task;
import org.nuxeo.runtime.api.Framework;

/**
 * Enricher that add a pending tasks on a document for the current user.
 *
 * @since 8.3
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class TasksJsonEnricher extends AbstractJsonEnricher<DocumentModel> {

    public static final String NAME = "tasks";

    public TasksJsonEnricher() {
        super(NAME);
    }

    @Override
    public void write(JsonGenerator jg, DocumentModel document) throws IOException {
        jg.writeFieldName(NAME);
        jg.writeStartArray();
        try (SessionWrapper wrapper = ctx.getSession(document)) {
            DocumentRoutingService documentRoutingService = Framework.getService(DocumentRoutingService.class);
            List<Task> tasks = documentRoutingService.getTasks(document, wrapper.getSession().getPrincipal().getName(),
                    null, null, wrapper.getSession());
            OutputStream out = new OutputStreamWithJsonWriter(jg);
            Writer<Task> taskWriter = registry.getWriter(ctx, Task.class, APPLICATION_JSON_TYPE);
            for (Task task : tasks) {
                taskWriter.write(task, Task.class, Task.class, APPLICATION_JSON_TYPE, out);
            }
        } finally {
            jg.writeEndArray();
        }
    }

}
