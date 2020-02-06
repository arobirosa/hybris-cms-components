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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScript;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptExecutionException;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptStep;

import java.io.File;
import java.io.IOException;

/**
 * It represents a step which only uses one single file.
 * 
 * @author arobirosa
 * 
 */
public abstract class AbstractSingleFileScriptStep implements DeploymentScriptStep {

    private File scriptFile;

    @Override
    public String getId() {
        if (this.scriptFile == null) {
            return null;
        }
        return this.scriptFile.getName();
    }

    protected String loadFileContent() throws DeploymentScriptExecutionException {
        String sqlStatement;
        try {
            sqlStatement = FileUtils.readFileToString(this.getScriptFile(), DeploymentScript.DEFAULT_FILE_ENCODING);
        } catch (final IOException e) {
            throw new DeploymentScriptExecutionException("There was an error while reading the contents of the SQL Script " + this.getId() + ':'
                    + e.getLocalizedMessage(), e);
        }
        if (StringUtils.isBlank(sqlStatement)) {
            throw new DeploymentScriptExecutionException("The file " + this.getScriptFile() + " is empty.");
        }
        return sqlStatement;
    }

    public File getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(final File scriptFile) {
        this.scriptFile = scriptFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName());
        builder.append(" [scriptFile=");
        builder.append(scriptFile);
        builder.append("]");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((scriptFile == null) ? 0 : scriptFile.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractSingleFileScriptStep other = (AbstractSingleFileScriptStep) obj;
        if (scriptFile == null) {
            if (other.scriptFile != null) {
                return false;
            }
        } else if (!scriptFile.equals(other.scriptFile)) {
            return false;
        }
        return true;
    }

}
