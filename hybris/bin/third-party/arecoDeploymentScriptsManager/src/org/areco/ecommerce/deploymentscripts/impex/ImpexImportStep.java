/**
 * Copyright 2014 Antonio Robirosa

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.areco.ecommerce.deploymentscripts.impex;

import de.hybris.platform.impex.jalo.ImpExException;

import org.apache.log4j.Logger;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptExecutionException;
import org.areco.ecommerce.deploymentscripts.core.impl.AbstractSingleFileScriptStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Represents a step of the deployment script were an impex file is imported.
 * 
 * @author arobirosa
 * 
 */
@Component
// Every time the step factory is called, it creates a new instance.
@Scope("prototype")
public class ImpexImportStep extends AbstractSingleFileScriptStep {
    private static final Logger LOG = Logger.getLogger(ImpexImportStep.class);

    @Autowired
    private ImpexImportService impexImportService;

    /*
     * { @InheritDoc }
     */
    @Override
    public void run() throws DeploymentScriptExecutionException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Running the step " + this.getId());
        }
        try {
            this.impexImportService.importImpexFile(this.getScriptFile());
        } catch (final ImpExException cause) {
            throw new DeploymentScriptExecutionException("There was an error importing the step " + this.getId(), cause);
        }
    }
}
