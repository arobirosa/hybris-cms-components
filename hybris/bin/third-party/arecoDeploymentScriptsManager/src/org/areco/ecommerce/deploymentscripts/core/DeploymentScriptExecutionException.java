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
package org.areco.ecommerce.deploymentscripts.core;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This is a checked exception because the caller has to manage it. It usually means setting the execution of the deployment script to some error state.
 * 
 * @author arobirosa
 * 
 */
public class DeploymentScriptExecutionException extends Exception {

    private static final int MAXIMUM_CAUSE_STACK_TRACE = 4096;

    /**
     * Default constructor with a message and a cause
     * 
     * @param message
     * @param cause
     */
    public DeploymentScriptExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a message.
     * 
     * @param message
     */
    public DeploymentScriptExecutionException(final String message) {
        super(message);
    }

    /**
     * Constructor with a cause
     * 
     * @param cause
     */
    public DeploymentScriptExecutionException(final Throwable cause) {
        super(cause);
    }

    public String getCauseShortStackTrace() {
        String output = this.getCauseFullStackTrace();
        if (output.length() > MAXIMUM_CAUSE_STACK_TRACE) {
          output = output.substring(0, MAXIMUM_CAUSE_STACK_TRACE - 1);
        }
        return output;
    }

    private String getCauseFullStackTrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        if (this.getCause() == null) {
            this.printStackTrace(printWriter);
        } else {
            this.getCause().printStackTrace(printWriter);
        }
        return stringWriter.toString();
    }
}
