x64 1-Wire Drivers Install Version 4.03
=======================================

This will install 64-bit 1-Wire Drivers Version 4.03 
on a computer running a 64-bit Microsoft operating system. 
This includes Windows 7 x64, Vista x64, XP SP2/3 x64, and 
Windows 2008 Server x64.

What gets installed are the low-level device drivers, the TMEX64
API, and a 64-bit version of the OneWireViewer as an executable.


Software Dependencies
=====================
1)  OneWireViewer requires an x64 Java Runtime Environment (JRE) to 
be installed before it can properly run.  Visit http://java.com with the 
x64 version of Internet Explorer to download an x64 JRE.
2)  The "Default 1-Wire Net" program is a .NET 2.0 program and requires 
the x64 .NET framework version 2.0 or higher to be installed before it can 
properly run.  Version 2.0 of .NET gets installed by default on Windows 
Vista x64.  If you have a down-level OS (such as XP), please make sure 
that x64 .NET 2.0 is installed.


Registry Keys Added
===================

Below is a list of registry keys added during this installation.  Please note that 
if a change to the default port is desired, please edit the "DefaultPortNum" and 
"DefaultPortType" entries in the system registry after installation.

[HKEY_CURRENT_USER\Software\Maxim Integrated Products]

[HKEY_LOCAL_MACHINE\SOFTWARE\Maxim Integrated Products\1-Wire Drivers]
"MainDriver"="IBFS32.DLL"
"TYPE1"="IB97E32.DLL"
"TYPE2"="IB10E32.DLL"
"TYPE5"="IB97U32.DLL"
"TYPE6"="IBUSB32.DLL"
"DefaultPortNum"="1"   *set by “Default 1-Wire Net” program
"DefaultPortType"="6"  *set by “Default 1-Wire Net” program
"DebugLevel"="0"          
"DebugFile"="c:\\onewirelog.txt"
"AltFlexSettings"="0"    

[HKEY_CURRENT_USER\Software\Maxim Integrated Products\1-Wire Drivers]
"MainDriver"="IBFS32.DLL"
"TYPE1"="IB97E32.DLL"
"TYPE2"="IB10E32.DLL"
"TYPE5"="IB97U32.DLL"
"TYPE6"="IBUSB32.DLL"
"DefaultPortNum"="1"   
"DefaultPortType"="6"  
"DebugLevel"="0"          
"DebugFile"="c:\\onewirelog.txt"
"AltFlexSettings"="0"

[HKEY_LOCAL_MACHINE\SOFTWARE\Maxim Integrated Products\1-Wire Drivers\x64]
"MainDriver"="IBFS64.DLL"
"TYPE1"="IB97E64.DLL"
"TYPE2"="IB10E64.DLL"
"TYPE5"="IB97U64.DLL"
"TYPE6"="IBUSB64.DLL"
"DefaultPortNum"="1"   *set by “Default 1-Wire Net” program
"DefaultPortType"="6"  *set by “Default 1-Wire Net” program
"DebugLevel"="0"          
"DebugFile"="c:\\onewirelog.txt"
"AltFlexSettings"="0"

[HKEY_CURRENT_USER\SOFTWARE\Maxim Integrated Products\1-Wire Drivers]
"MainDriver"="IBFS32.DLL"
"TYPE1"="IB97E32.DLL"
"TYPE2"="IB10E32.DLL"
"TYPE5"="IB97U32.DLL"
"TYPE6"="IBUSB32.DLL"
"DefaultPortNum"="1" 
"DefaultPortType"="6"  
"DebugLevel"="0"          
"DebugFile"="c:\\onewirelog.txt"
"AltFlexSettings"="0"


Special Installation Considerations
===================================

* Do not plug in any 1-Wire USB device until <after>
  the installation completes.



USB Adapter Enumeration Notes
=============================

 1)  The way 1-Wire USB adapter (specifically, the DS9490R 
     or DS9490B) enumeration occurs is a bit different from 
     other 1-Wire adapters as some TMEX USB port numbers are 
     reserved.  
 2)  The 1-Wire USB driver supports hot-swap for the default 
     device on Port number 1. 
 3)  Port numbers 2 though 15 are assigned to a particular 
     adapter based on it's unique number. This port will remain 
     the same even after the PC is rebooted or devices are 
     removed and reinserted.  
 4)  Port 0 is reserved for DS2490-based adapters with no 
     unique number assigned.


Uninstall
=========
To uninstall this package, do the following:

1. Click on the 'Start' button
2. Click on 'Settings' and then 'Control Panel'
3. Double-click on the 'Add/Remove Programs' 
   icon in the 'Control Panel'
4. Double-click the '1-Wire Drivers' line in the 
   'Add/Remove Programs' window and follow
   the prompts.


More Information
================

  1-Wire/iButton information:
    http://www.maxim-ic.com/auto_info.cfm

  Maxim Integrated Products information:
    http://www.maxim-ic.com/

  Technical Support:
    http://www.maxim-ic.com/support/tech_support/router.mvp

