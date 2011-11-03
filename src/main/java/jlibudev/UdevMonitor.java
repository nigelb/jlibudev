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

package jlibudev;

import jlibudev.generated.UdevLibrary;
import jlibudev.generated.udev_monitor;

/**
 * <code>UdevMonitor</code> wraps {@link udev_monitor} and provides convenience methods.
 * to its functions.
 *
 * @Author NigelB
 */
public class UdevMonitor {
    private UdevLibrary la;
    udev_monitor udev_monitor;

    public UdevMonitor(UdevLibrary la, udev_monitor udev_monitor) {
        this.la = la;
        this.udev_monitor = udev_monitor;
    }

    public void udev_monitor_filter_add_match_subsystem_devtype(String subsystem, String devtypes) {
        la.udev_monitor_filter_add_match_subsystem_devtype(udev_monitor, subsystem, devtypes);
    }

    public void udev_monitor_enable_receiving() {
        la.udev_monitor_enable_receiving(udev_monitor);
    }

    public int udev_monitor_get_fd() {
        return la.udev_monitor_get_fd(udev_monitor);
    }

    public UdevDevice udev_monitor_receive_device() {
        return new UdevDevice(la, la.udev_monitor_receive_device(udev_monitor));
    }
}
