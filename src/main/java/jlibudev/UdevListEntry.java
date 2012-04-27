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
 * <code>UdevListEntry</code> wraps a udev_list_entry pointer and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevListEntry {
    private UdevLibrary la;
    private UdevLibrary.udev_list_entry list_entry;
    private Udev udev;

    public UdevListEntry(UdevLibrary la, UdevLibrary.udev_list_entry list_entry, Udev udev) {
        this.la = la;
        this.list_entry = list_entry;
        this.udev = udev;
    }

    public UdevDevice getDevice()
    {
        return new UdevDevice(la, la.udev_device_new_from_syspath(udev.getInternal(), getName()));
    }

    public String getName()
    {
        return la.udev_list_entry_get_name(list_entry);
    }

    public String getValue()
    {
        return la.udev_list_entry_get_value(list_entry);
    }

}
