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
import jlibudev.generated.udev;

/**
 * <code>Udev</code> wraps {@link udev} and provides convenience methods.
 *
 *
 * @Author NigelB
 */
public class Udev {
    private UdevLibrary la;
    udev udev;

    Udev(UdevLibrary la, udev udev) {
        this.la = la;
        this.udev = udev;
    }

    public String getSysPath()
    {
        return udev.sys_path.getString(0);
    }
    public  UdevMonitor createMonitor(String name)
    {
        return new UdevMonitor(la, la.udev_monitor_new_from_netlink(udev, name));
    }

    public  UdevEnumerate createEnumeration()
    {
        return new UdevEnumerate(la, la.udev_enumerate_new(udev), this);
    }

    @Override
    protected void finalize() throws Throwable {
        try{
            if(udev != null)
            {
//                la.udev_unref(udev);
            }
        }finally {
            super.finalize();
        }
    }
}
