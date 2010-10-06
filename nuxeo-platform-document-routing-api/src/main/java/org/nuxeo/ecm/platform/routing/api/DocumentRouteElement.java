/*
 * (C) Copyright 2009 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     arussel
 */
package org.nuxeo.ecm.platform.routing.api;

import java.io.Serializable;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;

/**
 *
 * @author arussel
 *
 */
public interface DocumentRouteElement extends Serializable {
    enum ElementLifeCycleState {
        draft, validated, ready, running, done
    }

    enum ElementLifeCycleTransistion {
        toValidated, toReady, toRunning, toDone
    }

    DocumentModelList getAttachedDocuments(CoreSession session);

    DocumentRoute getDocumentRoute(CoreSession session);

    boolean isValidated();

    boolean isReady();

    boolean isDone();

    boolean isRunning();

    String getName();

    String getDescription();

    /**
     *
     * @param session
     * @return true is the element is not done
     */
    void run(CoreSession session);

    void validate(CoreSession session) throws ClientException;

    DocumentModel getDocument();

    /**
     * save the document representing this DocumentRoute.
     *
     * @param session
     */
    void save(CoreSession session);

    void setValidated(CoreSession session);

    void setReady(CoreSession session);

    void setRunning(CoreSession session);

    void setDone(CoreSession session);

    void setReadOnly(CoreSession session) throws ClientException;

    void followTransition(ElementLifeCycleTransistion transition,
            CoreSession session, boolean recursive);

    boolean canValidateStep(CoreSession session);

    void setCanValidateStep(CoreSession session, String userOrGroup);

    boolean canUpdateStep(CoreSession session);

    void setCanUpdateStep(CoreSession session, String userOrGroup);

    boolean canDeleteStep(CoreSession session);

    void setCanDeleteStep(CoreSession session, String userOrGroup);
}