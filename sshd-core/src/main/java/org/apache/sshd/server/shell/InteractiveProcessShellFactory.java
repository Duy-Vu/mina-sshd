/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.sshd.server.shell;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.apache.sshd.common.util.OsUtils;

/**
 * A simplistic interactive shell factory
 * @author <a href="mailto:dev@mina.apache.org">Apache MINA SSHD Project</a>
 */
public class InteractiveProcessShellFactory extends ProcessShellFactory {
    // must come first due to class loading issues
    private static final String[] LINUX_COMMAND = { "/bin/sh", "-i", "-l" };
    private static final String[] WINDOWS_COMMAND = { "cmd.exe" };

    public static String[] resolveInteractiveCommand(boolean isWin32) {
        // return clone(s) to avoid inadvertent modification
        if (isWin32) {
            return WINDOWS_COMMAND.clone();
        } else {
            return LINUX_COMMAND.clone();
        }
    }
    
    public static final Set<TtyOptions> LINUX_OPTIONS =
            Collections.unmodifiableSet(EnumSet.of(TtyOptions.ONlCr));
    public static final Set<TtyOptions> WINDOWS_OPTIONS =
            Collections.unmodifiableSet(EnumSet.of(TtyOptions.Echo, TtyOptions.ICrNl, TtyOptions.ONlCr));

    public static Set<TtyOptions> resolveTtyOptions(boolean isWin32) {
        if (isWin32) {
            return WINDOWS_OPTIONS;
        } else {
            return LINUX_OPTIONS;
        }
    }

    public static final InteractiveProcessShellFactory INSTANCE = new InteractiveProcessShellFactory();

    public InteractiveProcessShellFactory() {
        super(resolveInteractiveCommand(OsUtils.isWin32()), resolveTtyOptions(OsUtils.isWin32()));
    }
}