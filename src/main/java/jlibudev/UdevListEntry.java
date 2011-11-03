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

package jlibudev;

import jlibudev.generated.UdevLibrary;
import jlibudev.generated.udev_list_entry;

/**
 * <code>UdevListEntry</code> wraps {@link udev_list_entry} and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevListEntry {
    private UdevLibrary la;
    private udev_list_entry list_entry;
    private Udev udev;

    public UdevListEntry(UdevLibrary la, udev_list_entry list_entry, Udev udev) {
        this.la = la;
        this.list_entry = list_entry;
        this.udev = udev;
    }

    public UdevDevice getDevice()
    {
        return new UdevDevice(la, la.udev_device_new_from_syspath(udev.udev, getName()));
    }

    public String getName()
    {
        return list_entry.name.getString(0);
    }

}
