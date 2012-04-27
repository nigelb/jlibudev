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

import java.io.File;
import java.io.FilenameFilter;

/**
 * <code>Udev</code> wraps udev_device and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevDevice {
    private UdevLibrary la;
    private UdevLibrary.udev_device udev_device;

    public UdevDevice(UdevLibrary la, UdevLibrary.udev_device udev_device) {
        this.la = la;
        this.udev_device = udev_device;
    }

    public Udev getUdev()
    {
        return new Udev(la, la.udev_device_get_udev(udev_device));
    }

    public UdevDevice getParent() {
        return new UdevDevice(la, la.udev_device_get_parent(udev_device));
    }


    public String getDevnode() {
        return la.udev_device_get_devnode(udev_device);
    }

    public String getSysPath() {
        return la.udev_device_get_syspath(udev_device);
    }

    public String getDevPath() {
        return la.udev_device_get_devpath(udev_device);
    }


    public String getSubsystem() {
        return la.udev_device_get_subsystem(udev_device);
    }

    public String getDevtype() {
        return la.udev_device_get_devtype(udev_device);
    }

    public String getDriver() {
        return la.udev_device_get_driver(udev_device);
    }

    public String getAction() {
        return la.udev_device_get_action(udev_device);
    }

    public String getSysName()
    {
        return la.udev_device_get_sysname(udev_device);
    }

    public UdevDevice udev_device_get_parent_with_subsystem_devtype(String subsystem, String devtype) {
        return new UdevDevice(la, la.udev_device_get_parent_with_subsystem_devtype(udev_device, subsystem, devtype));
    }

    public String getSysattrValue(String key) {
        return la.udev_device_get_sysattr_value(udev_device, key);
    }

    /**
     * <code>getSysattrKeys</code> returns an array of all available sysattrs for this device.
     *
     * @return String[] the sysattr keys.
     */
    public String[] getSysattrKeys() {
        File f = new File(getSysPath());
        return f.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !new File(dir, name).isDirectory();
            }
        });

    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(String.format("Action: %s, ", getAction())).append(
                String.format("SysPath: %s, ", getSysPath())).append(
                String.format("DevPath: %s, ", getDevPath())).append(
                String.format("Node: %s, ", getDevnode())).append(
                String.format("Subsystem: %s, ", getSubsystem())).append(
                String.format("Devtype: %s, ", getDevtype())).append(
                String.format("SysName: %s, ", getSysName())).append(
                String.format("Driver: %s", getDriver()));
        String del = "";
        if (getAction() == null || getAction().equals("add")) {
            buf.append(", Keys: [");
            for (String s : getSysattrKeys()) {
                buf.append(del).append(s).append(": ").append(getSysattrValue(s));
                del=", ";
            }
            buf.append("]");
        }
        return buf.toString();

    }

    protected UdevLibrary.udev_device getInternal() {
        return udev_device;
    }
}
