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

package jlibudev.examples;

import jlibudev.UdevDevice;
import jlibudev.monitor.AlreadyRunningException;
import jlibudev.monitor.DeviceListener;
import jlibudev.monitor.HardwareMonitor;

/**
 * <code>HardwareMonitorExample</code> a simple example using the {@link HardwareMonitor}
 *
 * @author NigelB
 */
public class HardwareMonitorExample {
    public static void main(String[] args) throws AlreadyRunningException {
        HardwareMonitor hm = new HardwareMonitor(true);
        hm.addListener(new DeviceListener() {
            @Override
            public void addDevice(UdevDevice dev) {
                System.out.println("Added: "+dev.getDevPath());
            }

            @Override
            public void removeDevice(UdevDevice dev) {
                System.out.println("Removed: "+dev.getDevPath());
            }

            @Override
            public void enumerateDevice(UdevDevice dev) {
                System.out.println("Enumerated: "+dev.getDevPath());
            }
        });
        hm.start();
    }
}
