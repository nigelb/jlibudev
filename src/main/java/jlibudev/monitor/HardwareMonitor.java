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

import java.util.Iterator;

import static jtermios.JTermios.*;

/**
 * Date: 4/27/12
 * Time: 2:43 PM
 *
 * @Author NigelB
 */
public class HardwareMonitor implements Runnable {
    private Thread t;
    private boolean running = false;
    private boolean enumerate;
    private boolean daemonize;
    private Udev ud = jlibUdev.createUdev();
    private UdevMonitor mon = ud.createMonitor("udev");


    public HardwareMonitor(boolean enumerate) {
        this(enumerate, false);
    }

    public HardwareMonitor(boolean enumerate, boolean demonize) {
        this.enumerate = enumerate;
        this.daemonize = demonize;
    }

    public void run() {
        if (enumerate) {
            UdevEnumerate enu = ud.createEnumeration();
            Iterator<UdevListEntry> di = enu.getScanIterator();
            UdevListEntry entry;
            while (di.hasNext()) {
                entry = di.next();
                System.out.println(entry);
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
                System.out.println("\nselect() says there should be data");

                UdevDevice dev = mon.receiveDevice();

                if (dev != null) {
                    System.out.println(dev);
                } else {
                    System.out.println("No Device from receive_device(). An error occured.");
                }
            }
            System.out.print(".");
        }

    }

    public void start() {
        if (t == null) {
            running = true;
            t = new Thread(this);
            t.setName("Udev Hardware Monitor");
            if (daemonize) {
                t.setDaemon(daemonize);
            }
            t.start();
        } else {

        }
    }

    public void stop(boolean wait_for_it)
    {
        running = false;
        if(wait_for_it)
        {
            try {
                t.join();
            } catch (InterruptedException e) {
//                logger.throwing();
            }
        }
    }

    public static void main(String[] args) {
        HardwareMonitor hm = new HardwareMonitor(true);
        hm.start();
    }

}
