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

import jlibudev.Udev;
import jlibudev.UdevDevice;
import jlibudev.UdevMonitor;
import jlibudev.jlibUdev;
import jtermios.FDSet;
import jtermios.JTermios;
import jtermios.TimeVal;

import static jtermios.JTermios.*;


/**
 * <code>UdevMonitorExample</code> provides an example of using libudev to monitor
 * the addition/removal/etc of devices.
 *
 * JNAed version of http://www.signal11.us/oss/udev/
 */
public class UdevMonitorExample {

    public static void main(String[] args) {
        Udev ud = jlibUdev.createUdev();
        UdevMonitor mon = ud.createMonitor("udev");
        mon.udev_monitor_filter_add_match_subsystem_devtype(null, null);
        mon.udev_monitor_enable_receiving();
        int fd = mon.udev_monitor_get_fd();

        FDSet fds = JTermios.newFDSet();
        TimeVal tv = new TimeVal();

        while (true) {

            FD_ZERO(fds);
            FD_SET(fd, fds);

            tv.tv_sec = 0;
            tv.tv_usec = 250000;

            int ret = select(fd + 1, fds, null, null, tv);

            if (ret > 0 && FD_ISSET(fd, fds)) {
                System.out.println("\nselect() says there should be data");

                UdevDevice dev = mon.udev_monitor_receive_device();

                if (dev != null) {
                    System.out.format("Action: %s, ", dev.getAction());
                    System.out.format("Node: %s, ", dev.getDevnode());
                    System.out.format("Subsystem: %s, ", dev.getSubsystem());
                    System.out.format("Devtype: %s, ", dev.getDevtype());
                    System.out.format("Driver: %s.\n", dev.getDriver());
                } else {
                    System.out.println("No Device from receive_device(). An error occured.");
                }
            }
            System.out.print(".");
        }
    }

}
