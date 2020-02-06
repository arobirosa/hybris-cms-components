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
package org.areco.ecommerce.deploymentscripts.scriptinglanguages.impl;

import org.apache.log4j.Logger;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptExecutionException;
import org.areco.ecommerce.deploymentscripts.core.impl.AbstractSingleFileScriptStep;
import org.areco.ecommerce.deploymentscripts.scriptinglanguages.ScriptingLanguageExecutionException;
import org.areco.ecommerce.deploymentscripts.scriptinglanguages.ScriptingLanguageExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * It represents a groovy script.
 * 
 * @author arobirosa
 * 
 */
@Component
// Every time the step factory is called, it creates a new instance.
@Scope("prototype")
public class ScriptingLanguageStep extends AbstractSingleFileScriptStep {

    private static final Logger LOG = Logger.getLogger(ScriptingLanguageStep.class);

    @Autowired
    private ScriptingLanguageExecutionService scriptingLanguageExecutionService;

    /*
     * Runs the script represented by this step.
     * 
     * @see org.areco.ecommerce.deploymentscripts.core.DeploymentScriptStep#run()
     */
    @Override
    public void run() throws DeploymentScriptExecutionException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Running the script " + this.getId());
        }
        try {
            this.scriptingLanguageExecutionService.executeScript(this.getScriptFile());
        } catch (final ScriptingLanguageExecutionException e) {
            throw new DeploymentScriptExecutionException("There was an error running the script step " + this.getId() + ": " + e.getLocalizedMessage(), e);
        }
    }

}
