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
import jlibudev.generated.udev_enumerate;
import jlibudev.generated.udev_list_entry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <code>UdevEnumerate</code> wraps {@link udev_enumerate} and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevEnumerate {
    private UdevLibrary la;
    private udev_enumerate udev_enumerate;
    private Udev udev;

    public UdevEnumerate(UdevLibrary la, udev_enumerate udev_enumerate, Udev udev) {
        this.la = la;
        this.udev_enumerate = udev_enumerate;
        this.udev = udev;
    }

    public int udev_enumerate_add_match_subsystem(String subsystem) {
        return la.udev_enumerate_add_match_subsystem(udev_enumerate, subsystem);
    }


    public Iterator<UdevListEntry> getScanIterator() {
        ArrayList<UdevListEntry> toRet = new ArrayList();

        la.udev_enumerate_scan_devices(udev_enumerate);
        return new Iterator<UdevListEntry>() {
            udev_list_entry list_entry = la.udev_enumerate_get_list_entry(udev_enumerate);

            public boolean hasNext() {
                return list_entry != null;
            }

            public UdevListEntry next() {
                UdevListEntry toRet = new UdevListEntry(la, list_entry, udev);
                list_entry = la.udev_list_entry_get_next(list_entry);
                return toRet;
            }

            public void remove() {
                throw new RuntimeException("Remove not implemented.");
            }
        };
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (udev_enumerate != null) {
                la.udev_enumerate_unref(udev_enumerate);
            }
        } finally {
            super.finalize();
        }

    }
}
