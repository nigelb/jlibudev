package jlibudev.monitor;

import jlibudev.UdevDevice;

/**
 * Date: 29/04/12
 * Time: 10:37 AM
 *
 * @author Nigel Bajema
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
