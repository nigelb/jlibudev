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

import com.sun.jna.NativeLong;
import jlibudev.generated.UdevLibrary;

/**
 * <code>Udev</code> wraps a udev pointer and provides convenience methods.
 *
 *
 * @Author NigelB
 */
public class Udev {
    private UdevLibrary la;
    private UdevLibrary.udev udev;

    Udev(UdevLibrary la, UdevLibrary.udev udev) {
        this.la = la;
        this.udev = udev;
    }

    public String getSysPath()
    {
        return la.udev_get_sys_path(udev);
    }

    public String getRunPath()
    {
        return la.udev_get_run_path(udev);
    }

    public String getDevPath()
    {
        return la.udev_get_dev_path(udev);
    }

    public  UdevMonitor createMonitor(String name)
    {
        return new UdevMonitor(la, la.udev_monitor_new_from_netlink(udev, name));
    }

    public  UdevEnumerate createEnumeration()
    {
        return new UdevEnumerate(la, la.udev_enumerate_new(udev), this);
    }

    public UdevDevice udev_device_new_from_devnum(byte type, long dev_num)
    {
        return new UdevDevice(la, la.udev_device_new_from_devnum(udev, type, new NativeLong(dev_num)));
    }

    public UdevDevice udev_device_new_from_subsystem_sysname(String subsystem, String sysname)
    {
        return new UdevDevice(la, la.udev_device_new_from_subsystem_sysname(udev, subsystem, sysname));
    }

    public UdevDevice udev_device_new_from_environment()
    {
        return new UdevDevice(la, la.udev_device_new_from_environment(udev));
    }

    protected UdevLibrary.udev getInternal()
    {
        return udev;
    }


}
