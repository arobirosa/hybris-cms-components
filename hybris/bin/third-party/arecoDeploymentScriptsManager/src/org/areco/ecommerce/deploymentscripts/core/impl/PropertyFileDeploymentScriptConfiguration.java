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
package org.areco.ecommerce.deploymentscripts.core.impl;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.Tenant;
import org.areco.ecommerce.deploymentscripts.core.DeploymentEnvironmentDAO;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptConfiguration;
import org.areco.ecommerce.deploymentscripts.core.ScriptExecutionResultDAO;
import org.areco.ecommerce.deploymentscripts.model.ScriptExecutionResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * It defines special properties of the deployment scripts like where they are allowed to run.
 * 
 * @author arobirosa
 * 
 */
// Every time the step factory is called, it creates a new instance.
@Scope("prototype")
@Component
public class PropertyFileDeploymentScriptConfiguration implements DeploymentScriptConfiguration {
    @Autowired
    private ScriptExecutionResultDAO scriptExecutionResultDAO;

    @Autowired
    private DeploymentEnvironmentDAO deploymentEnvironmentDAO;

    /* The existent of the tenants is validated during the creation of the configuration. */
    private Set<Tenant> allowedTenants;
    /*
     * It contains the names of the environment because we validate the existenz of it just before running the script.
     */
    private Set<String> allowedDeploymentEnvironmentNames;

    /**
     * Checks if this script is allowed to run in this server.
     * 
     * @return null if it is allowed to run. Otherwise it returns the execution result.
     */
    @Override
    public ScriptExecutionResultModel reasonToIgnoreExecutionOnThisServer() {
        if (!this.isAllowedInThisTenant()) {
            return this.scriptExecutionResultDAO.getIgnoredOtherTenantResult();
        }
        if (!this.isAllowedInThisDeploymentEnvironment()) {
            return this.scriptExecutionResultDAO.getIgnoredOtherEnvironmentResult();
        }
        return null; // We can run this script
    }

    private boolean isAllowedInThisDeploymentEnvironment() {
        if (this.getAllowedDeploymentEnvironmentNames() == null || this.getAllowedDeploymentEnvironmentNames().isEmpty()) {
            return true;
        }
        return this.isCurrentEnvironmentIn(this.getAllowedDeploymentEnvironmentNames());
    }

    private boolean isAllowedInThisTenant() {
        if (this.getAllowedTenants() == null || this.getAllowedTenants().isEmpty()) {
            return true;
        }
        return this.getAllowedTenants().contains(this.getCurrentTenant());
    }

    /**
     * Returns the current tenant
     * 
     * @return Never null
     */
    private Tenant getCurrentTenant() {
        return Registry.getCurrentTenant();
    }

    /**
     * Determines of the current environment is in the given list of names.
     * 
     * @param deploymentEnvironmentNames
     *            Required
     * @return true if the current environment is present.
     */
    private boolean isCurrentEnvironmentIn(final Set<String> deploymentEnvironmentNames) {
        return this.deploymentEnvironmentDAO.loadEnvironments(deploymentEnvironmentNames).contains(this.deploymentEnvironmentDAO.getCurrent());
    }

    /**
     * Returns the allowed tenants.
     * 
     * @return Never null.
     */
    public Set<Tenant> getAllowedTenants() {
        return allowedTenants;
    }

    /**
     * Sets the allowed tenants.
     * 
     * @param allowedTenants
     *            Required
     */
    public void setAllowedTenants(final Set<Tenant> allowedTenants) {
        this.allowedTenants = allowedTenants;
    }

    /**
     * Getter of the environments
     * 
     * @return Never null
     */
    public Set<String> getAllowedDeploymentEnvironmentNames() {
        return allowedDeploymentEnvironmentNames;
    }

    /**
     * Setter of the environments
     * 
     */
    public void setAllowedDeploymentEnvironmentNames(final Set<String> allowedDeploymentEnvironmentNames) {
        this.allowedDeploymentEnvironmentNames = allowedDeploymentEnvironmentNames;
    }
}
