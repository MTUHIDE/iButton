package handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.ClockContainer;
import com.dalsemi.onewire.container.OneWireContainer;

import ntp.NTPMessage;

/**
 * This class handles communication with the iButton that deals with time.
 * 
 * @author Justin Havely
 *
 */
public class ClockHandler {

	/**
	 * Used to get the clock container of the iButton. This container is require
	 * to read and write clock related data.
	 * 
	 * @param adapter
	 *            The adapter the iButton is plugged into.
	 * @return Null if no clock container could be found.
	 * @throws OneWireIOException
	 *             If no iButton could be found.
	 * @throws OneWireException
	 *             If no iButton could be found.
	 */
	public static ClockContainer getClockContainer(DSPortAdapter adapter) throws OneWireIOException, OneWireException {
		boolean device = adapter.findFirstDevice();

		while (device) {
			OneWireContainer owc = adapter.getDeviceContainer();
			device = adapter.findNextDevice();
			ClockContainer container;
			try {
				container = (ClockContainer) owc;
				return container;
			} catch (Exception e) {
				continue;
			}

		}
		return null;
	}

	/**
	 * Sets the real time clock of the iButton to UTC using a NTP server.
	 * 
	 * @param adapter
	 *            The adapter the iButton is plugged into.
	 * @return Null if no clock container could be found.
	 * @throws OneWireIOException
	 *             If no iButton could be found.
	 * @throws OneWireException
	 *             If no iButton could be found.
	 */
	public static void setClock(DSPortAdapter adapter, ClockContainer container)
			throws OneWireIOException, OneWireException {
		adapter.beginExclusive(true);
		byte[] state = container.readDevice();
		try {
			long time = getUTCTime();
			container.setClock(time, state);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!container.isClockRunning(state)) {
			container.setClockRunEnable(true, state);
		}
		container.writeDevice(state);

		adapter.endExclusive();
	}

	/**
	 * 
	 * Gets the time (UTC) using the "time.nist.gov" NTP server. Will try to
	 * connect five times before throwing an exception.
	 * 
	 * @return The time in millisecond since 1970 UTC. -1 if an error happen.
	 * @throws IOException
	 *             If the connection times out five times.
	 */
	private static long getUTCTime() throws IOException {
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName("time.nist.gov");
		byte[] buf = new NTPMessage().toByteArray();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
		socket.setSoTimeout(5000);

		for (int i = 0; i < 5; i++) {
			socket.send(packet);
			try {
				socket.receive(packet);
				socket.close();
				return NTPMessage.timestampToMilli(NTPMessage.decodeTimestamp(packet.getData(), 40));
			} catch (SocketTimeoutException e) {
				if (i == 4) {
					socket.close();
					throw new IOException();
				}
				continue;
			}
		}
		socket.close();
		return -1;
	}

}
