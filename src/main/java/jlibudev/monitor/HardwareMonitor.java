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

import jlibudev.*;
import jtermios.FDSet;
import jtermios.JTermios;
import jtermios.TimeVal;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static jtermios.JTermios.*;

/**
 * <code>HardwareMonitor</code> monitors hardware changes and notifies listeners of these changes.
 *
 * @Author NigelB
 */
public class HardwareMonitor implements Runnable {
    private static Logger logger = Logger.getLogger(HardwareMonitor.class);
    private Thread t, dispatcher;
    private boolean running = false;
    private boolean enumerate;
    private boolean daemonize;
    private Udev ud = jlibUdev.createUdev();
    private UdevMonitor mon = ud.createMonitor("udev");
    private LinkedBlockingQueue<UdevDevice> devices = new LinkedBlockingQueue<UdevDevice>();
    private CopyOnWriteArrayList<DeviceListener> listeners = new CopyOnWriteArrayList<DeviceListener>();


    public HardwareMonitor(boolean enumerate) {
        this(enumerate, false);
    }

    public HardwareMonitor(boolean enumerate, boolean demonize) {
        this.enumerate = enumerate;
        this.daemonize = demonize;
    }

    public void addListener(DeviceListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(DeviceListener listener)
    {
        listeners.remove(listener);
    }

    public void run() {
        if (enumerate) {
            UdevEnumerate enu = ud.createEnumeration();
            Iterator<UdevListEntry> di = enu.getScanIterator();
            UdevListEntry entry;
            UdevDevice dev;
            while (di.hasNext()) {
                entry = di.next();
                dev = entry.getDevice();
                devices.offer(dev);
            }
        }
        mon.enable();
        int fd = mon.getFD();

        FDSet fds = JTermios.newFDSet();
        TimeVal tv = new TimeVal();

        while (running) {
            FD_ZERO(fds);
            FD_SET(fd, fds);

            tv.tv_sec = 0;
            tv.tv_usec = 250000;

            int ret = select(fd + 1, fds, null, null, tv);

            if (ret > 0 && FD_ISSET(fd, fds)) {
                logger.trace("select() says there should be data");

                UdevDevice dev = mon.receiveDevice();

                if (dev != null) {
                    devices.offer(dev);
                } else {
                    logger.error("No Device from receive_device(). An error occurred.");
                }
            }
        }

    }

    private void dispatch()
    {
        UdevDevice dev;
        while(running)
        {
            try {
                dev = devices.poll(200, TimeUnit.MILLISECONDS);
                if(dev != null)
                {
                    for (DeviceListener listener : listeners) {
                        listener.deviceEvent(dev);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() throws AlreadyRunningException {
        if (t == null && dispatcher == null) {
            running = true;
            t = new Thread(this);
            t.setName("Udev Hardware Monitor");

            dispatcher = new Thread(new Runnable() {
                @Override
                public void run() {
                    HardwareMonitor.this.dispatch();
                }
            });
            dispatcher.setName("Udev Device Dispatcher");
            if (daemonize) {
                t.setDaemon(daemonize);
                dispatcher.setDaemon(daemonize);
            }
            t.start();
            dispatcher.start();

        } else {
            throw new AlreadyRunningException("The Hardware Monitor is already running.");
        }
    }

    public void stop(boolean wait_for_it)
    {
        running = false;
        if(wait_for_it)
        {
            try {
                t.join();
                t = null;
                dispatcher.join();
                dispatcher = null;
            } catch (InterruptedException e) {
                logger.debug("Interrupted", e);
            }
        }
    }

}
