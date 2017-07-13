# iButton
This is the CoCo iTemp application source code for the CoCoTemp project. This 
application is for managing CoCo Temp siteControllers that are connected to an iButton iButton.

The CoCo Temp website can be found here: <https://hci-dev.cs.mtu.edu:8103/cocotemp/>
For help on using CoCo iTemp App go to: <https://www.dropbox.com/s/92gtlkrrdi9aagx/CoCoiTempUserGuide.pdf?dl=0>

The CoCo iTemp installer can be download here: 
#### Windows 10, 8.1, 7 
- 32-bit: <https://www.dropbox.com/s/qpd8xam69sreb1v/CoCoiTemp_x86.msi?dl=0>
- 64-bit: <https://www.dropbox.com/s/150dm0ni04vadyy/CoCoiTemp_x64.msi?dl=0>

## Helpful Tips
One Wire Drives are require if you did not used the installer: <https://www.maximintegrated.com/en/products/ibutton/software/tmex/download_drivers.cfm>

For an example on using the ibutton to communicate with iButton devices run the DeviceControll class in the example package.

The main method is in the app.IButtonApp class in the gui package.

The URL endpoints are in the enum CocoTempURLs.

## To Do list
- ~~Clean networking code to use one API.~~
- Progress Bars for uploading, editing, and adding siteControllers.
- Use Google's API to find Latitude and Longitude.
- Update Theme to match CoCo Temp siteController's theme.
- Register page in the CoCo iTemp app.
- Add desktop icon to installer.
- ~~32 bit installer.*~~
- ~~Add Device status (Not assign, Not plugged in) to dashboard.*~~
- ~~Only allow one iButton to be assign to one siteController.*~~
- Checkbox for iButton edit and check "add/edit siteController" fields for correct text.

* "*" = Important