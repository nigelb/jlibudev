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

/**
 * <code>UdevMonitor</code> wraps a udev_monitor pointer and provides convenience methods.
 * to its functions.
 *
 * @Author NigelB
 */
public class UdevMonitor {
    private UdevLibrary la;
    UdevLibrary.udev_monitor udev_monitor;

    public UdevMonitor(UdevLibrary la, UdevLibrary.udev_monitor udev_monitor) {
        this.la = la;
        this.udev_monitor = udev_monitor;
    }

    public void enable() {
        la.udev_monitor_enable_receiving(udev_monitor);
    }

    public int getFD() {
        return la.udev_monitor_get_fd(udev_monitor);
    }

    public UdevDevice receiveDevice() {
        return new UdevDevice(la, la.udev_monitor_receive_device(udev_monitor));
    }

    public int setReceiveBufferSize(int size){
        return la.udev_monitor_set_receive_buffer_size(udev_monitor, size);
    }

    public void filterAddMatchSubsystemDevtype(String subsystem, String devtypes) {
        la.udev_monitor_filter_add_match_subsystem_devtype(udev_monitor, subsystem, devtypes);
    }

    public int filterAddMatchTag(String tag)
    {
        return la.udev_monitor_filter_add_match_tag(udev_monitor, tag);
    }

    public int filterUpdate()
    {
        return la.udev_monitor_filter_update(udev_monitor);
    }

    public int filterRemove()
    {
        return la.udev_monitor_filter_remove(udev_monitor);
    }
}
