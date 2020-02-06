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

import de.hybris.bootstrap.config.ConfigUtil;
import de.hybris.bootstrap.config.ExtensionInfo;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.apache.log4j.Logger;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScript;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptConfigurationReader;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptFinder;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptStep;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptStepFactory;
import org.areco.ecommerce.deploymentscripts.core.ScriptExecutionDao;
import org.areco.ecommerce.deploymentscripts.enums.SystemPhase;
import org.areco.ecommerce.deploymentscripts.model.ScriptExecutionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Default implementation of the deployment script finder.
 * 
 * @author arobirosa
 * 
 */
// The configuration of this bean is in the spring application context.
@SuppressWarnings("PMD") // The import of all the classes from the core package is correct
public abstract class ArecoDeploymentScriptFinder implements DeploymentScriptFinder {
    private static final Logger LOG = Logger.getLogger(ArecoDeploymentScriptFinder.class);

    public static final String UPDATE_SCRIPTS_FOLDER_CONF = "deploymentscripts.update.folder";

    public static final String INIT_SCRIPTS_FOLDER_CONF = "deploymentscripts.init.folder";

    public static final String RESOURCES_FOLDER_CONF = "deploymentscripts.resources.folder";

    @Autowired
    private ScriptExecutionDao scriptExecutionDao;

    @Autowired
    private List<DeploymentScriptStepFactory> stepFactories;

    @Autowired
    private DeploymentScriptConfigurationReader configurationReader;

    @Autowired
    private ConfigurationService configurationService;

    /** {@inheritDoc} */
    @Override
    public List<DeploymentScript> getPendingScripts(final String extensionName, final Process process, final boolean runInitScripts) {
        ServicesUtil.validateParameterNotNullStandardMessage("extensionName", extensionName);
        ServicesUtil.validateParameterNotNullStandardMessage("process", process);
        final List<File> pendingScriptsFolders = getScriptsToBeRun(extensionName, runInitScripts);
        return getDeploymentScripts(pendingScriptsFolders, extensionName, process);
    }

    private List<File> getScriptsToBeRun(final String extensionName, final boolean runInitScripts) {
        final List<String> alreadyExecutedScripts = getAlreadyExecutedScripts(extensionName);

        final List<File> pendingScriptsFolders = new ArrayList<>();
        for (final File foundScriptFolder : getExistingScripts(extensionName, runInitScripts)) {
            if (!alreadyExecutedScripts.contains(foundScriptFolder.getName())) {
                pendingScriptsFolders.add(foundScriptFolder);
            }
        }
        sortFilesCaseInsensitive(pendingScriptsFolders);
        return pendingScriptsFolders;
    }

    /**
     * It sorts the given collection ignoring the case of the filenames. This results in the same order of the files in Windows and Unix-like systems.
     * 
     * All the files MUST be in the same directory because the path isn't compared.
     * 
     * @param files
     *            Required
     */
    private void sortFilesCaseInsensitive(final List<File> files) {
        Collections.sort(files, Comparator.comparing(f -> f.getName().toLowerCase(Locale.getDefault())));
    }

    private List<String> getAlreadyExecutedScripts(final String extensionName) {
        final List<String> alreadyExecutedScripts = new ArrayList<>();
        for (final ScriptExecutionModel alreadyExecutedScript : this.scriptExecutionDao.getSuccessfullyExecutedScripts(extensionName)) {
            alreadyExecutedScripts.add(alreadyExecutedScript.getScriptName());
        }
        return alreadyExecutedScripts;
    }

    private File[] getExistingScripts(final String extensionName, final boolean runInitScripts) {
        final ExtensionInfo extension = ConfigUtil.getPlatformConfig(ArecoDeploymentScriptFinder.class).getExtensionInfo(extensionName);
        String scriptsFolderName;
        if (runInitScripts) {
            scriptsFolderName = this.configurationService.getConfiguration().getString(INIT_SCRIPTS_FOLDER_CONF);
        } else {
            scriptsFolderName = this.configurationService.getConfiguration().getString(UPDATE_SCRIPTS_FOLDER_CONF);
        }
        return getExistingScriptsInDirectory(extension, scriptsFolderName);
    }

    private File[] getExistingScriptsInDirectory(final ExtensionInfo extension, final String scriptsFolderName) {
        final File deploymentScriptFolder = new File(extension.getExtensionDirectory()
                + this.configurationService.getConfiguration().getString(RESOURCES_FOLDER_CONF), scriptsFolderName);
        if (LOG.isDebugEnabled()) {
                LOG.debug("Looking for scripts in '" + deploymentScriptFolder.getAbsolutePath() + '\'');
        }
        if (!deploymentScriptFolder.exists()) {
            return new File[0];
        }
        return deploymentScriptFolder.listFiles(pathname -> pathname.isDirectory());
    }

    /**
     * Converts the given folders to deployment script instances.
     * 
     * @param pendingScriptsFolders
     *            Required
     * @param extensionName
     *            Required
     * @return Never null
     */
    private List<DeploymentScript> getDeploymentScripts(final List<File> pendingScriptsFolders, final String extensionName, final Process process) {
        final List<DeploymentScript> newDeploymentScripts = new ArrayList<>();

        for (final File pendingScriptsFolder : pendingScriptsFolders) {
            final DeploymentScript newScript = createDeploymentScript(pendingScriptsFolder, extensionName, process);
            if (newScript != null) {
                newDeploymentScripts.add(newScript);
            }
        }
        return newDeploymentScripts;
    }

    private DeploymentScript createDeploymentScript(final File deploymentScriptFolder, final String extensionName, final Process process) {
        final List<DeploymentScriptStep> orderedSteps = createOrderedSteps(deploymentScriptFolder);

        if (orderedSteps.isEmpty()) {
            return null;
        }
        final DeploymentScript newScript = this.newDeploymentScript();
        newScript.setName(deploymentScriptFolder.getName());
        newScript.setExtensionName(extensionName);
        newScript.setOrderedSteps(orderedSteps);
        newScript.setConfiguration(configurationReader.loadConfiguration(deploymentScriptFolder));
        if (LOG.isTraceEnabled()) {
            LOG.trace("Current Hybris process: " + process);
        }
        if (SystemSetup.Process.INIT.equals(process)) {
            newScript.setPhase(SystemPhase.INITIALIZATION);
        } else {
            // In a normal Update Running System, the value of process is ALL.
            newScript.setPhase(SystemPhase.UPDATE);
        }
        return newScript;
    }

    private List<DeploymentScriptStep> createOrderedSteps(final File scriptFolder) {
        final List<DeploymentScriptStep> steps = new ArrayList<>();
        File[] foundFiles = scriptFolder.listFiles(pathname -> pathname.isFile());
        if (foundFiles == null) {
            foundFiles = new File[] {};
        }
        final List<File> sortedFiles = Arrays.asList(foundFiles);

        sortFilesCaseInsensitive(sortedFiles);
        for (final File impexFile : sortedFiles) {
            for (final DeploymentScriptStepFactory aStepFactory : this.stepFactories) {
                final DeploymentScriptStep newStep = aStepFactory.create(impexFile);
                if (newStep != null) {
                    steps.add(newStep);
                    break; // After the a step is created for a file, we ignore the next factories.
                }
            }

        }
        return steps;
    }

    /**
     * Creates an empty instance of the deployment script class.
     * 
     * @return Never null.
     */
    protected abstract DeploymentScript newDeploymentScript();
}
