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


package jlibudev.monitor;

import jlibudev.UdevDevice;

/**
 * <code>ActionMultiplexer</code> multiplexes the device action into the appropriate DeviceListener call.
 *
 * @author NigelB
 */
public enum ActionMultiplexer {
    ADD("add"){
        @Override
        public void fireEvent(DeviceListener listener, UdevDevice dev) {
            listener.addDevice(dev);
        }
    },
    REMOVE("remove"){
        @Override
        public void fireEvent(DeviceListener listener, UdevDevice dev) {
            listener.removeDevice(dev);
        }
    },
    ENUMERATION("null"){
        @Override
        public void fireEvent(DeviceListener listener, UdevDevice dev) {
            listener.enumerateDevice(dev);
        }
    };
    private String action;

    ActionMultiplexer(String action) {
        this.action = action;
    }

    public abstract void fireEvent(DeviceListener listener, UdevDevice dev);

    public static ActionMultiplexer findAction(String action) {
        if (action == null) {
            action = "null";
        }
        for (ActionMultiplexer actionType : values()) {
            if (actionType.action.equalsIgnoreCase(action))
            {
                return actionType;
            }
        }
        throw new IllegalArgumentException(String.format("No enum constant %s.%s", ActionMultiplexer.class.getCanonicalName(), action));
    }
}
