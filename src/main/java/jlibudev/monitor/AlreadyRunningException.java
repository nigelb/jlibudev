/*
* jlibudev provides JNA access to libudev.
* Copyright (C) 2011 NigelB
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/

package jlibudev.monitor;

/**
 * <code>AlreadyRunningException</code> thrown by the HardwareMonitor if start is called when it is already running.
 *
 * @author NigelB
 */
public class AlreadyRunningException extends Exception {
    public AlreadyRunningException() {
        super();
    }

    public AlreadyRunningException(String message) {
        super(message);
    }

    public AlreadyRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyRunningException(Throwable cause) {
        super(cause);
    }

}
