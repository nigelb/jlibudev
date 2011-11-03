/*
* jlibudev provides JNA access to udev.
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

import jlibudev.*;

import java.util.Iterator;

/**
 * <code>UdevEnumerationExample</code> provides an example of using libudev to enumerate the
 * systems devices.
 *
 * JNAed version of http://www.signal11.us/oss/udev/
 */
public class UdevEnumerationExample {
    public static void main(String[] args) {
        Udev ud = jlibUdev.createUdev();
        UdevEnumerate enu = ud.createEnumeration();
        enu.udev_enumerate_add_match_subsystem("hidraw");
        Iterator<UdevListEntry> di = enu.getScanIterator();
        UdevDevice parent;
        while (di.hasNext()) {
            UdevListEntry next = di.next();
            parent = next.getDevice().udev_device_get_parent_with_subsystem_devtype("usb", "usb_device");
            if(parent == null)
            {
                System.out.println("Unable to find parent usb device.");
                System.exit(1);
            }
            System.out.println(next.getName());
            System.out.println(parent.getSysPath());
            String[] keys = parent.getSysattrKeys();
            for (String key : keys) {
                System.out.printf("\t--%s: %s%n", key, parent.getSysattrValue(key));
            }
        }
    }
}
