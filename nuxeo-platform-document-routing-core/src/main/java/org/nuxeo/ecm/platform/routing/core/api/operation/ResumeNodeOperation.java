/*
 * Copyright (c) 2013 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariana Cedica
 */
package org.nuxeo.ecm.platform.routing.core.api.operation;

import java.util.HashMap;

import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.platform.routing.api.DocumentRoutingService;

/**
 * Resumes a workflow on a give node. If nodeId is null, is applies to the
 * current node of the given workflow resumed.
 *
 * @since 5.7.2
 *
 */
@Operation(id = ResumeNodeOperation.ID, category = Constants.CAT_WORKFLOW, label = "Resume workflow", requires = Constants.WORKFLOW_CONTEXT, description = "Resumes a route instance on a give node.")
public class ResumeNodeOperation {
    public static final String ID = "Workflow.ResumeNodeOperation";

    @Context
    protected CoreSession session;

    @Context
    protected OperationContext ctx;

    @Param(name = "workflowInstanceId")
    protected String workflowInstanceId;

    @Param(name = "nodeId", required = false)
    protected String nodeId;

    @Context
    protected DocumentRoutingService documentRoutingService;

    @OperationMethod
    public void resumeWorkflow() throws ClientException {
        if (nodeId == null) {
            nodeId = (String) ctx.get("nodeId");
        }
        if (nodeId == null) {
            throw new ClientException(
                    "Can not resume workflow instance with id "
                            + workflowInstanceId
                            + ". No current node in the context");
        }
        documentRoutingService.resumeInstance(workflowInstanceId, nodeId,
                new HashMap<String, Object>(), null, session);
    }
}