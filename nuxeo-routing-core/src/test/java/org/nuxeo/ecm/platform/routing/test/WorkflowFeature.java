/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Stephane Lacoin
 */
package org.nuxeo.ecm.platform.routing.test;

import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.LocalDeploy;
import org.nuxeo.runtime.test.runner.SimpleFeature;

@Features(AutomationFeature.class)
@Deploy({ "org.nuxeo.ecm.platform.content.template", //
        "org.nuxeo.ecm.automation.core", //
        "org.nuxeo.ecm.platform.usermanager", //
        "org.nuxeo.ecm.platform.userworkspace.core", //
        "org.nuxeo.ecm.platform.userworkspace.types", //
        "org.nuxeo.ecm.platform.task.api", //
        "org.nuxeo.ecm.platform.task.core", //
        "org.nuxeo.ecm.platform.routing.api", "org.nuxeo.ecm.platform.routing.core" //
})
@LocalDeploy({ "org.nuxeo.ecm.platform.routing.core:OSGI-INF/test-sql-directories-contrib.xml",
        "org.nuxeo.ecm.platform.routing.core:OSGI-INF/test-graph-operations-contrib.xml",
        "org.nuxeo.ecm.platform.routing.core:OSGI-INF/test-graph-types-contrib.xml",
        "org.nuxeo.ecm.platform.audit:OSGI-INF/core-type-contrib.xml"})
public class WorkflowFeature extends SimpleFeature {

}
