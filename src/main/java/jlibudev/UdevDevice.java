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
import jlibudev.generated.udev_device;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <code>Udev</code> wraps {@link udev_device} and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevDevice {
    private UdevLibrary la;
    private udev_device udev_device;

    public UdevDevice(UdevLibrary la, udev_device udev_device) {
        this.la = la;
        this.udev_device = udev_device;
    }

    public UdevDevice getParent() {
        return new UdevDevice(la, la.udev_device_get_parent(udev_device));
    }


    public String getDevnode() {
        if (udev_device.devnode == null) {
            return null;
        }
        return udev_device.devnode.getString(0);
    }

    public String getSysPath() {
        if (udev_device.syspath == null) {
            return null;
        }
        return udev_device.syspath.getString(0);
    }

    public String getDevPath() {
        if (udev_device.devpath == null) {
            return null;
        }
        return udev_device.devpath.getString(0);
    }


    public String getSubsystem() {
        if (udev_device.subsystem == null) {
            return null;
        }
        return udev_device.subsystem.getString(0);
    }

    public String getDevtype() {
        if (udev_device.devtype == null) {
            return null;
        }
        return udev_device.devtype.getString(0);
    }

    public String getDriver() {
        if (udev_device.driver == null) {
            return null;
        }
        return udev_device.driver.getString(0);
    }

    public String getAction() {
        if (udev_device.action == null) {
            return null;
        }
        return udev_device.action.getString(0);
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
                if (new File(dir, name).isDirectory()) {
                    return false;
                }
                return true;
            }
        });

    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (udev_device != null) {
//                la.udev_device_unref(udev_device);
            }
        } finally {
            super.finalize();
        }
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
                String.format("Driver: %s.", getDriver()));
        return buf.toString();

    }
}
