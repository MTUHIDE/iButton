# iButton
This is the CoCo iTemp application source code for the CoCoTemp project. This 
application is for managing CoCo Temp sites that are connected to an iButton device.

The CoCo Temp website can be found here: <https://hci-dev.cs.mtu.edu:8103/cocotemp/>
For help on using CoCo iTemp App go to: <https://www.dropbox.com/s/aktgj9itxcry05i/CoCoiButtonAppUserGuide.pdf?dl=0>
Email problems to: jmh628@nau.edu

The CoCo iTemp installer can be download here: 
#### Windows 10, 8.1, 7 
- 32-bit: <https://www.dropbox.com/s/s0yjmqgmtnn0blr/CoCoiTemp_x86.msi?dl=0>
- 64-bit: <https://www.dropbox.com/s/wm7chhfdh651iko/CoCoiTemp_x64.msi?dl=0>

## Helpful Tips
One Wire Drives are require if you did not used the installer: <https://www.maximintegrated.com/en/products/ibutton/software/tmex/download_drivers.cfm>

For an example on using the handlers to communicate with One Wire devices run the DeviceControll class in the example package.

The main method is in the IButtonApp class in the gui package.

## To Do list
- ~~Clean networking code to use one API.~~
- Progress Bars for uploading, editing, and adding sites.
- Use Google's API to find Latitude and Longitude.
- Update Theme to match CoCo Temp site's theme.
- Register page in the CoCo iTemp app.
- Add desktop icon to installer.
- ~~32 bit installer.*~~
- Add Device status (Not assign, Not plugged in) to dashboard.*
- ~~Only allow one device to be assign to one site.*~~
- Checkbox for device edit and check "add/edit site" fields for correct text.*

* "*" = Important