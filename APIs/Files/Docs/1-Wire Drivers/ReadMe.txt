1-Wire Drivers Version 4.03 (32-bit)
====================================

This will install 1-Wire Drivers Version 4.03 on this computer. 1-Wire Drivers will enable 1-Wire applications to communicate with Maxim's 1-Wire and iButton products on 32-bit Microsoft Windows 7, Vista, XP (Service pack 2 and higher), and Windows 2008 Server.

Please take note the supported 32-bit Microsoft Platforms mentioned above.  If legacy platform support is needed (such as Windows 98 or Windows 2000), please use a previous version of the 1-Wire Drivers, specifically, Version 4.00.  Of particular note for this release is the switch to using version 2.0 of Microsoft's generic userland device driver and API known as WinUSB and added support for multi-user installs.  The DS2490.SYS is now considered legacy.


Special Considerations for USB:
===============================

1.  Do not plug-in any 1-Wire USB device until after the install has completed.
2.  If having difficulty upgrading a machine to use WinUSB versus the legacy DS2490.sys, please update the driver by clicking "Start->Control Panel->System".  Then click on the "Hardware" tab and click the "Device Manager" button.  Double-click an entry entitled "1-Wire Devices" or "1Wire Devices".  Then right-click the resulting sub-entry and choose "Update Driver".  An install wizard will appear.  Point the wizard to the directory where the 1-Wire drivers were copied (usually under "Program Files"), and look for a subdirectory named "WinUSB_Driver".  Select this subdirectory.  If problems are encountered, see the USB troubleshooting guide in the Appendix of White Paper 6, located online here:

http://www.maxim-ic.com/appnotes.cfm/appnote_number/1740


USB Adapter Enumeration Notes:
==============================

1.  The way 1-Wire USB adapter (specifically, the DS9490R or DS9490B) enumeration occurs is a bit different from other 1-Wire adapters as some TMEX USB port numbers are reserved. 
2.  Port number 1 is reserved for a duplicate handle to the first adapter found in the USB enumeration.
3.  Port numbers 2 though 15 are assigned to a specific adapter based on it's unique number. This port will remain the same even after the PC is rebooted or devices are removed and reinserted.
4.  Port 0 is reserved for DS2490-based adapters with no unique number assigned.


The 1-Wire Drivers installation comes with a general purpose software application, the "OneWireViewer".  This versatile software utility enables the experimentor to examine, test, and maintain iButtons and other 1-Wire devices.

Before upgrading to this version of 1-Wire Drivers, always remove any previous version with its uninstaller.  To use the uninstaller in Windows:

1. Click on the 'Start' button
2. Click on 'Settings' and then 'Control Panel'
3. Double-click on the 'Add/Remove Programs' or 'Programs and Features'.
4. Select the '1-Wire Drivers' line in the resulting window.
5. Click the "Remove" or "Uninstall" button. 
6. Follow the remaining prompts until finished.


For more information

  iButton information:
    http://www.ibutton.com/

  Maxim information:
    http://www.maxim-ic.com/

  Technical Support:
    http://www.maxim-ic.com/products/ibutton/contacts/

