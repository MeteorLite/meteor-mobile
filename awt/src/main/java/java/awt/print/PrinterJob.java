/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.awt.print;

import java.awt.AWTError;
import java.awt.HeadlessException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.harmony.awt.internal.nls.Messages;

public abstract class PrinterJob {

    /* abstract section */
    public abstract void cancel();

    public abstract void setPrintable(Printable painter);

    public abstract void setPrintable(Printable painter, PageFormat format);

    public abstract void setPageable(Pageable document)
            throws NullPointerException;

    public abstract void print() throws PrinterException;

    public abstract void setJobName(String jobName);

    public abstract void setCopies(int copies);

    public abstract int getCopies();

    public abstract boolean printDialog() throws HeadlessException;

    public abstract boolean isCancelled();

    public abstract String getUserName();

    public abstract String getJobName();

    public abstract PageFormat pageDialog(PageFormat page)
            throws HeadlessException;

    public abstract PageFormat defaultPage(PageFormat page);

    public abstract PageFormat validatePage(PageFormat page);

    /* static section */
    public static PrinterJob getPrinterJob(){

        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null) {
            securitymanager.checkPrintJobAccess();
        }
        /* This code has been developed according to API documentation
         * for Priviledged Blocks. 
         */
        return AccessController.doPrivileged(
                new PrivilegedAction<PrinterJob>() {
            public PrinterJob run() {
                String s = org.apache.harmony.awt.Utils.getSystemProperty("java.awt.printerjob"); //$NON-NLS-1$

                if (s == null || s.equals("")){ //$NON-NLS-1$
                    s = "java.awt.print.PrinterJobImpl"; //$NON-NLS-1$
                }
                try {
                    return (PrinterJob) Class.forName(s).newInstance();
                } catch (ClassNotFoundException cnfe) {
                    // awt.5A=Default class for PrinterJob is not found
                    throw new AWTError(Messages.getString("awt.5A")); //$NON-NLS-1$
                } catch (IllegalAccessException iae) {
                    // awt.5B=No access to default class for PrinterJob
                    throw new AWTError(Messages.getString("awt.5B")); //$NON-NLS-1$
                } catch (InstantiationException ie) {
                    // awt.5C=Instantiation exception for PrinterJob
                    throw new AWTError(Messages.getString("awt.5C")); //$NON-NLS-1$
                }
            }
        });
    }


    /* public section*/
    public PrinterJob() {
        super();
     }

     public PageFormat defaultPage(){
        return defaultPage(new PageFormat());
     }

}
