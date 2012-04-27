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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <code>UdevEnumerate</code> wraps a udev_enumerate pointer and provides convenience methods.
 *
 * @Author NigelB
 */
public class UdevEnumerate {
    private UdevLibrary la;
    private UdevLibrary.udev_enumerate udev_enumerate;
    private Udev udev;

    public UdevEnumerate(UdevLibrary la, UdevLibrary.udev_enumerate udev_enumerate, Udev udev) {
        this.la = la;
        this.udev_enumerate = udev_enumerate;
        this.udev = udev;
    }

    public int addMatchSubsystem(String subsystem) {
        return la.udev_enumerate_add_match_subsystem(udev_enumerate, subsystem);
    }

    public int addSysPath(String syspath)
    {
        return la.udev_enumerate_add_syspath(udev_enumerate, syspath);
    }

    public int addNomatchSubsystem(String subsystem)
    {
        return la.udev_enumerate_add_nomatch_subsystem(udev_enumerate, subsystem);
    }

    public int addMatchSysattr(String sysattr, String value)
    {
        return la.udev_enumerate_add_match_sysattr(udev_enumerate, sysattr, value);
    }

    public int addNomatchSysattr(String sysattr, String value)
    {
        return la.udev_enumerate_add_nomatch_sysattr(udev_enumerate, sysattr, value);
    }

    public int addMatchProperty(String property, String value)
    {
        return la.udev_enumerate_add_match_property(udev_enumerate, property, value);
    }

    public int addMatchSysname(String sysname)
    {
        return la.udev_enumerate_add_match_sysname(udev_enumerate, sysname);
    }

    public int addMatchTag(String tag)
    {
        return la.udev_enumerate_add_match_tag(udev_enumerate, tag);
    }

    public int addMatchParent(UdevDevice parent)
    {
        return la.udev_enumerate_add_match_parent(udev_enumerate, parent.getInternal());
    }

    public int addMatchIsInitialized()
    {
        return la.udev_enumerate_add_match_is_initialized(udev_enumerate);
    }

    public int scanSubSubsystems()
    {
        return la.udev_enumerate_scan_subsystems(udev_enumerate);
    }

    public Iterator<UdevListEntry> getScanIterator() {
        la.udev_enumerate_scan_devices(udev_enumerate);
        return new Iterator<UdevListEntry>() {
            UdevLibrary.udev_list_entry list_entry = la.udev_enumerate_get_list_entry(udev_enumerate);

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

}
